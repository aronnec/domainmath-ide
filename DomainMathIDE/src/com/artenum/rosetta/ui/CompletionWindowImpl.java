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
package com.artenum.rosetta.ui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Point;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JWindow;
import javax.swing.SwingUtilities;

import com.artenum.rosetta.interfaces.core.CompletionItem;
import com.artenum.rosetta.interfaces.core.InputParsingManager;
import com.artenum.rosetta.interfaces.ui.CompletionWindow;

/**
 * @author Sebastien Jourdain (jourdain@artenum.com)
 */
public class CompletionWindowImpl implements CompletionWindow, KeyListener, FocusListener, MouseMotionListener, MouseListener {
    private CompletionItemListModel model;
    private JList listUI;
    private JScrollPane scrollPane;
    private JWindow window;
    private InputParsingManager inputParsingManager;
    private JComponent focusOutComponent;

    public CompletionWindowImpl() {
    }

    @Override
    public void setFocusOut(final JComponent component) {
        focusOutComponent = component;
    }

    @Override
    public void setInputParsingManager(final InputParsingManager inputParsingManager) {
        this.inputParsingManager = inputParsingManager;
    }

    /**
     * Caution, the component shouldn't be null otherwise the completion window will never get the focus
     */
    @Override
    public void setGraphicalContext(final Component component) {
        if (component instanceof Frame) {
            window = new JWindow((Frame) component);
        } else {
            window = new JWindow(SwingUtilities.getWindowAncestor(component));
        }

        model = new CompletionItemListModel();
        listUI = new JList(model);
        listUI.setCellRenderer(new CompletionItemListCellRenderer());
        scrollPane = new JScrollPane(listUI, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        final JLabel windowResizeCorner = new JLabel("~", JLabel.CENTER);
        windowResizeCorner.addMouseMotionListener(this);
        scrollPane.setCorner(JScrollPane.LOWER_RIGHT_CORNER, windowResizeCorner);
        window.getContentPane().add(scrollPane);
        window.setSize(new Dimension(300, 100));

        // Overide Listener
        listUI.getInputMap().clear();
        scrollPane.getInputMap().clear();
        listUI.addKeyListener(this);
        listUI.addFocusListener(this);
    }

    @Override
    public void show(final List<CompletionItem> list, final Point location) {
        model.updateData(list);
        window.setLocation(location);
        window.setVisible(true);
        if (model.getSize() > 0) {
            listUI.setSelectedIndex(0);
        }
        // listUI.grabFocus();
    }

    @Override
    public String getCompletionResult() {
        return ((CompletionItem) listUI.getSelectedValue()).getReturnValue();
    }

    /**
     * List model which allow filter on completion item
     */

    public class CompletionItemListModel extends AbstractListModel {
        private static final long serialVersionUID = 1L;
        private final ArrayList<CompletionItem> data;
        private final ArrayList<CompletionItem> filteredData;
        private String filter;

        public CompletionItemListModel() {
            data = new ArrayList<CompletionItem>();
            filteredData = new ArrayList<CompletionItem>();
        }

        @Override
        public Object getElementAt(final int index) {
            return (filter != null) ? filteredData.get(index) : data.get(index);
        }

        @Override
        public int getSize() {
            return (filter != null) ? filteredData.size() : data.size();
        }

        public void setFilter(final String filter) {
            if ((filter == null) || ((filter != null) && (filter.length() == 0))) {
                this.filter = null;
            } else {
                this.filter = filter;
                filteredData.clear();
                for (final CompletionItem item : data) {
                    if (item.getReturnValue().toLowerCase().startsWith(filter.toLowerCase())) {
                        filteredData.add(item);
                    }
                }
            }
            fireContentsChanged(this, 0, getSize());
        }

        public void updateData(final List<CompletionItem> list) {
            data.clear();
            data.addAll(list);
            Collections.sort(data);
            setFilter(null);
        }
    }

    /**
     * Management of the key typing for the filtering
     */
    @Override
    public void keyPressed(final KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            inputParsingManager.writeCompletionPart(((CompletionItem) listUI.getSelectedValue()).getReturnValue());
            window.setVisible(false);
            focusOutComponent.grabFocus();
        } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            // Do nothing
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            window.setVisible(false);
            focusOutComponent.grabFocus();
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            if (model.getSize() > 0) {
                listUI.setSelectedIndex((listUI.getSelectedIndex()) % model.getSize());
            }
        } else if (e.getKeyCode() == KeyEvent.VK_UP) {
            if (model.getSize() > 0) {
                listUI.setSelectedIndex((model.getSize() + listUI.getSelectedIndex()) % model.getSize());
            }
        } else if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
            if (inputParsingManager.getPartLevel(inputParsingManager.getCompletionLevel()).length() > 0) {
                inputParsingManager.backspace();
                model.setFilter(inputParsingManager.getPartLevel(inputParsingManager.getCompletionLevel()));
                listUI.setSelectedIndex(0);
            } else {
                window.setVisible(false);
                focusOutComponent.grabFocus();
            }
        } else {
            if (e.getKeyChar() != KeyEvent.CHAR_UNDEFINED) {
                inputParsingManager.append(String.valueOf(e.getKeyChar()));
                model.setFilter(inputParsingManager.getPartLevel(inputParsingManager.getCompletionLevel()));
                listUI.setSelectedIndex(0);
            }
        }
    }

    @Override
    public void keyReleased(final KeyEvent e) {
    }

    @Override
    public void keyTyped(final KeyEvent e) {
    }

    /**
     * To support the auto hide when focus is lost
     */

    @Override
    public void focusGained(final FocusEvent e) {
    }

    @Override
    public void focusLost(final FocusEvent e) {
        window.setVisible(false);
    }

    /**
     * To support the completion window resize
     */

    @Override
    public void mouseDragged(final MouseEvent e) {

        // Java 1.6
        // Point origine = window.getLocationOnScreen();
        // Point destination = e.getLocationOnScreen();
        // destination.translate(-origine.x, -origine.y);
        // window.setSize(destination.x, destination.y);

        final Point origine = window.getLocationOnScreen();
        final Point destination = ((Component) e.getSource()).getLocationOnScreen();
        destination.x += e.getX();
        destination.y += e.getY();
        destination.translate(-origine.x, -origine.y);
        window.setSize(destination.x, destination.y);
    }

    @Override
    public void mouseMoved(final MouseEvent e) {
    }

    @Override
    public void mouseClicked(final MouseEvent e) {
    }

    @Override
    public void mouseEntered(final MouseEvent e) {
    }

    @Override
    public void mouseExited(final MouseEvent e) {
    }

    @Override
    public void mousePressed(final MouseEvent e) {
    }

    @Override
    public void mouseReleased(final MouseEvent e) {
        window.validate();
    }

}
