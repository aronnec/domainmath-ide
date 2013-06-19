/*
 * Jalview - A Sequence Alignment Editor and Viewer (Version 2.8)
 * Copyright (C) 2012 J Procter, AM Waterhouse, LM Lui, J Engelhardt, G Barton, M Clamp, S Searle
 * 
 * This file is part of Jalview.
 * 
 * Jalview is free software: you can redistribute it and/or
 * modify it under the terms of the GNU General Public License 
 * as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *  
 * Jalview is distributed in the hope that it will be useful, but 
 * WITHOUT ANY WARRANTY; without even the implied warranty 
 * of MERCHANTABILITY or FITNESS FOR A PARTICULAR 
 * PURPOSE.  See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with Jalview.  If not, see <http://www.gnu.org/licenses/>.
 */
package jalview.ws.jws2;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import compbio.metadata.*;

public class ParameterUtils
{
  public static List<String> writeParameterSet(List<Option> optSet,
          String pseparator)
  {
    List<String> pset = new ArrayList<String>();
    for (Option o : optSet)
    {
      pset.add(o.toCommand(pseparator));
    }
    return pset;
  }

  /**
   * Converts options supplied via parameters file into {@code Option} objects
   * (Refactored from compbio.ws.client.Jws2Client)
   * 
   * @param <T>
   *          web service type
   * @param params
   * @param options
   * @return List of Options of type T
   * 
   */
  /*
   * @SuppressWarnings(value = { "true" }) public static <T> List<Option<T>>
   * processParameters(List<String> params, RunnerConfig<T> options, String
   * pseparator)
   */
  public static List<Option> processParameters(List<String> params,
          RunnerConfig options, String pseparator)
  {
    List<Option> chosenOptions = new ArrayList<Option>();
    for (String param : params)
    {
      String oname = null;
      if (isParameter(param, pseparator))
      {
        oname = getParamName(param, pseparator);
      }
      else
      {
        oname = param;
      }
      Option o = options.getArgumentByOptionName(oname);
      if (o == null)
      {
        System.out.println("WARN ignoring unsuppoted parameter: " + oname);
        continue;
      }
      if (o instanceof Parameter)
      {
        o = copyParameter((Parameter) o);
      }
      else
      {
        o = copyOption(o);
      }
      {
        try
        {
          o.setDefaultValue(isParameter(param, pseparator) ? getParamValue(
                  param, pseparator) : param);
        } catch (WrongParameterException e)
        {
          System.out.println("Problem setting value for the parameter: "
                  + param);
          e.printStackTrace();
        }
      }
      chosenOptions.add(o);
    }
    return chosenOptions;
  }

  static String getParamName(String fullName, String pseparator)
  {
    assert isParameter(fullName, pseparator);
    return fullName.substring(0, fullName.indexOf(pseparator));
  }

  static String getParamValue(String fullName, String pseparator)
  {
    assert isParameter(fullName, pseparator);
    return fullName.substring(fullName.indexOf(pseparator) + 1);
  }

  static boolean isParameter(String param, String pseparator)
  {
    return param.contains(pseparator);
  }

  public static Option copyOption(Option option)
  {
    Option copy = new Option(option.getName(), option.getDescription());
    setOptionFrom(copy, option);
    return copy;
  }

  public static void setOptionFrom(Option copy, Option option)
  {
    copy.setName(option.getName());
    copy.setDescription(option.getDescription());
    copy.setFurtherDetails(option.getFurtherDetails());
    copy.setRequired(option.isRequired());
    List<String> names = option.getOptionNames();
    if (names != null)
    {
      if (names.size() == 1)
      {
        HashSet<String> st = new HashSet();
        st.add(names.get(0));
        copy.setOptionNames(st);
      }
      else
      {
        copy.addOptionNames(names.toArray(new String[]
        {}));
      }
    }
    try
    {
      if (option.getDefaultValue() != null)
      {
        copy.setDefaultValue(option.getDefaultValue());
      }
    } catch (Exception ex)
    {
      ex.printStackTrace();
    }
  }

  public static ValueConstrain copyValueConstrain(ValueConstrain vc)
  {
    try
    {
      ValueConstrain copy = new ValueConstrain();
      if (vc.getMax() != null)
      {
        copy.setMax(vc.getMax().toString());
      }
      if (vc.getMin() != null)
      {
        copy.setMin(vc.getMin().toString());
      }
      if (vc.getType() != null)
      {
        copy.setType(vc.getType());
      }
      return copy;
    } catch (Exception e)
    {
      e.printStackTrace();
      throw new Error(
              "Implementation error: could not copy ValueConstrain!");
    }
  }

  public static Parameter copyParameter(Parameter parameter)
  {
    Parameter copy = new Parameter(parameter.getName(),
            parameter.getDescription());
    if (parameter.getValidValue() != null)
    {
      copy.setValidValue(copyValueConstrain(parameter.getValidValue()));
    }
    List<String> pv = parameter.getPossibleValues();
    if (pv != null)
    {
      copy.addPossibleValues(pv.toArray(new String[]
      {}));
    }
    setOptionFrom(copy, parameter);
    return copy;
  }

}
