/*
 * (c) Copyright: Artenum SARL, 24 rue Louis Blanc,
 *                75010, Paris, France 2007-2009.
 *                http://www.artenum.com
 *
 * License:
 *
 *  This program is free software; you can redistribute it
 *  and/or modify it under the terms of the license defined in the
 *  LICENSE.TXT file at the root of the present package.
 *
 *  This program is distributed in the hope that it will be
 *  useful, but WITHOUT ANY WARRANTY; without even the implied
 *  warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
 *  PURPOSE. See the LICENSE.TXT for more details.
 *
 *  You should have received a copy of the License along with 
 *  this program; if not, write to:
 *    Artenum SARL, 24 rue Louis Blanc,
 *    75010, PARIS, FRANCE, e-mail: contact@artenum.com
 */
package com.artenum.rosetta.util;

import java.awt.Event;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.TreeSet;

import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.KeyStroke;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.artenum.rosetta.interfaces.core.Configuration;
import com.artenum.rosetta.interfaces.core.ConsoleAction;
import com.artenum.rosetta.interfaces.core.ConsoleConfiguration;

/**
 * @author Sebastien Jourdain (jourdain@artenum.com)
 */

public class ConfigurationBuilder {
    protected static final String VALUE = "value";
    protected static final String NAME = "name";
    protected static final String ACTION = "action";
    protected static final String MASK = "mask";
    protected static final String CLASSNAME = "className";
    protected static final String USE_CACHE = "useCache";
    protected static final String KEY_MAPPING = "KeyMapping";
    protected static final String ACTION_MAPPING = "ActionMapping";
    protected static final String TRUE = "true";
    protected static final String FALSE = "false";
    protected static final String METHOD_GET = "get";
    protected static final String METHOD_IS = "is";
    protected static final String PRIMITIVE_TYPE_NAME_INT = "int";
    protected static final String PRIMITIVE_TYPE_NAME_DOUBLE = "double";
    protected static final String PRIMITIVE_TYPE_NAME_BOOLEAN = "boolean";
    private static String version = null;

    public static String getVersion() {
        if (version == null) {
            try {
                final InputStream inputStream = ConfigurationBuilder.class.getClassLoader().getResourceAsStream("version.txt");

                if (inputStream == null) {
                    return null;
                }

                final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                try {
                    version = bufferedReader.readLine();
                } finally {
                    bufferedReader.close();
                }

            } catch (final IOException e) {
                e.printStackTrace();
            }
        }
        return version;
    }

    public static Object buildConfiguration(final Class<?>[] resultClass, final String fileToLoad) throws IllegalArgumentException, SAXException, IOException,
            ParserConfigurationException {
        return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), resultClass, new ConfigurationHandler(fileToLoad));
    }

    public static ConsoleConfiguration buildConfiguration(final String fileToLoad) throws IllegalArgumentException, SAXException, IOException,
            ParserConfigurationException {
        return (ConsoleConfiguration) buildConfiguration(new Class[] { ConsoleConfiguration.class }, fileToLoad);
    }

    private static class ConfigurationHandler implements InvocationHandler, Configuration {
        private final Collection<String> internalMethodNames;
        private String activeProfile;
        private final Document dom;
        private final ArrayList<String> profileList;
        private final Hashtable<String, Object> cache;
        private boolean useCache;
        private final ActionMap actionMap;
        private final InputMap inputMap;
        private Object proxy;

        public ConfigurationHandler(final String xmlFile) throws SAXException, IOException, ParserConfigurationException {
            if (!new File(xmlFile).exists()) {
                throw new java.io.IOException("Configuration file " + xmlFile + " not found");

            }
            // Build dictionary for internal method
            internalMethodNames = new TreeSet<String>();
            final Method[] internalMethodes = this.getClass().getMethods();
            for (final Method method : internalMethodes) {
                internalMethodNames.add(method.getName());
            }

            // Build xml document for request
            dom = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new File(xmlFile));

            // Build profile table
            final NodeList profiles = dom.getElementsByTagName("Profile");
            profileList = new ArrayList<String>(profiles.getLength());
            for (int i = 0; i < profiles.getLength(); i++) {
                profileList.add(profiles.item(i).getAttributes().getNamedItem(NAME).getNodeValue());
            }
            // Init cache
            cache = new Hashtable<String, Object>();
            // Init Key/Actio mapping object
            inputMap = new InputMap();
            actionMap = new ActionMap();

        }

        @Override
        public void setActiveProfile(final String profileName) {
            activeProfile = profileName;
            inputMap.clear();
            actionMap.clear();
        }

        @Override
        public String getActiveProfile() {
            return activeProfile;
        }

        /*
         * Tries to match a method to a value provided by the XML
         * 
         * @param proxy
         * 
         * @param method
         * 
         * @param args
         */
        @Override
        public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
            this.proxy = proxy;
            // Managed internal method

            if (internalMethodNames.contains(method.getName())) {
                return getClass().getMethod(method.getName(), method.getParameterTypes()).invoke(this, args);
            }
            // Get String key+value
            String key = null;
            if (method.getName().startsWith(METHOD_GET)) {
                key = method.getName().substring(METHOD_GET.length());
            }
            if (method.getName().startsWith(METHOD_IS)) {
                key = method.getName().substring(METHOD_IS.length());
            }

            final String value = getPropertyValue(key);
            Object result = null;

            if (useCache) {
                result = cache.get(key);
                if (result != null) {
                    return result;
                }
            }

            if (method.getReturnType().equals(String.class)) {
                // Set String result
                result = value;
            } else if (value.length() > 0) {
                // Set Object result
                if (method.getReturnType().getName().equals(PRIMITIVE_TYPE_NAME_BOOLEAN)) {
                    result = Boolean.valueOf(value);
                } else if (method.getReturnType().getName().equals(PRIMITIVE_TYPE_NAME_INT)) {
                    result = Integer.valueOf(value);
                } else if (method.getReturnType().getName().equals(PRIMITIVE_TYPE_NAME_DOUBLE)) {
                    result = Double.valueOf(value);
                } else {
                    result = Class.forName(value).newInstance();
                }
            }

            // Save in cache if needed

            if (result != null && useCache) {
                cache.put(key, result);
            }

            // Return result
            return result;
        }

        /**
         * @return
         */
        private int getActiveProfileIndex() {
            final int result = profileList.indexOf(activeProfile);
            return (result > -1) ? result : 0;
        }

        /**
         * get the value of the propertie
         * 
         * @param propertyName
         *            the name of the propertie
         * @return the value
         */
        private String getPropertyValue(final String propertyName) {
            final NodeList elements = dom.getElementsByTagName(propertyName);
            if (elements.getLength() == 0) {
                return "";
            }
            final NamedNodeMap attributes = elements.item(getActiveProfileIndex()).getAttributes();
            useCache = attributes.getNamedItem(USE_CACHE) != null && attributes.getNamedItem(USE_CACHE).getNodeValue().equalsIgnoreCase(TRUE);
            return attributes.getNamedItem(VALUE).getNodeValue();
        }

        /*
         * Load informations from the configuration files which defines what method should be called regarding an action
         * 
         * @return
         * 
         * @see getKeyMapping
         */
        @Override
        public ActionMap getActionMapping() {
            if (actionMap.size() == 0) {
                final NodeList actionElements = dom.getElementsByTagName(ACTION_MAPPING).item(getActiveProfileIndex()).getChildNodes();
                Node element = null;
                String key = null;
                ConsoleAction action = null;
                for (int i = 0; i < actionElements.getLength(); i++) {
                    element = actionElements.item(i);
                    if (element.getNodeType() == Node.TEXT_NODE) {
                        continue;
                    }
                    key = element.getAttributes().getNamedItem(NAME).getNodeValue();
                    try {
                        action = (ConsoleAction) Class.forName(element.getAttributes().getNamedItem(CLASSNAME).getNodeValue()).newInstance();
                        action.setConfiguration((ConsoleConfiguration) proxy);
                        actionMap.put(key, action);
                    } catch (final Exception e) {
                        e.printStackTrace();
                        System.err.println("Impossible to process ActionMapping with key=" + key);
                    }
                }
            }
            return actionMap;
        }

        /*
         * Load informations from the configuration files which defines a mapping between a key and an action name
         * 
         * @return
         */
        @Override
        public InputMap getKeyMapping() {
            if (inputMap.size() == 0) {
                final NodeList keyElements = dom.getElementsByTagName(KEY_MAPPING).item(getActiveProfileIndex()).getChildNodes();
                Node element = null;
                String name = null;
                String mask = null;
                String action = null;
                for (int i = 0; i < keyElements.getLength(); i++) {
                    element = keyElements.item(i);
                    if (element.getNodeType() == Node.TEXT_NODE) {
                        continue;
                    }
                    name = element.getAttributes().getNamedItem(NAME).getNodeValue();
                    mask = element.getAttributes().getNamedItem(MASK).getNodeValue();
                    action = element.getAttributes().getNamedItem(ACTION).getNodeValue();
                    try {
                        inputMap.put(KeyStroke.getKeyStroke(convertKeyInformation(name), convertKeyInformation(mask)), action);
                    } catch (final Exception e) {
                        System.err.println("Impossible to process getKeyMapping with name=" + name + " mask=" + mask + " action=" + action);
                    }
                }
            }
            return inputMap;
        }

        /**
         * @param value
         * @return
         */
        private int convertKeyInformation(final String value) {
            if (value == null || value.trim().length() == 0) {
                return 0;
            }
            try {
                return Integer.parseInt(value);
            } catch (final Exception e) {
                try {
                    return ((Integer) KeyEvent.class.getField(value).get(null)).intValue();
                } catch (final Exception ee) {
                    try {
                        return ((Integer) Event.class.getField(value).get(null)).intValue();
                    } catch (final Exception eee) {
                        return 0;
                    }
                }
            }

        }
    }

}
