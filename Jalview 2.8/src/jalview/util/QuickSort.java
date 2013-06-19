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
package jalview.util;

public class QuickSort
{
  public static void sort(int[] arr, Object[] s)
  {
    sort(arr, 0, arr.length - 1, s);
  }

  public static void sort(float[] arr, Object[] s)
  {
    sort(arr, 0, arr.length - 1, s);
  }

  public static void sort(double[] arr, Object[] s)
  {
    sort(arr, 0, arr.length - 1, s);
  }

  public static void sort(String[] arr, Object[] s)
  {
    stringSort(arr, 0, arr.length - 1, s);
  }

  public static void stringSort(String[] arr, int p, int r, Object[] s)
  {
    int q;

    if (p < r)
    {
      q = stringPartition(arr, p, r, s);
      stringSort(arr, p, q, s);
      stringSort(arr, q + 1, r, s);
    }
  }

  public static void sort(float[] arr, int p, int r, Object[] s)
  {
    int q;

    if (p < r)
    {
      q = partition(arr, p, r, s);
      sort(arr, p, q, s);
      sort(arr, q + 1, r, s);
    }
  }

  public static void sort(double[] arr, int p, int r, Object[] s)
  {
    int q;

    if (p < r)
    {
      q = partition(arr, p, r, s);
      sort(arr, p, q, s);
      sort(arr, q + 1, r, s);
    }
  }

  public static void sort(int[] arr, int p, int r, Object[] s)
  {
    int q;

    if (p < r)
    {
      q = partition(arr, p, r, s);
      sort(arr, p, q, s);
      sort(arr, q + 1, r, s);
    }
  }

  private static int partition(float[] arr, int p, int r, Object[] s)
  {
    float x = arr[p];
    int i = p - 1;
    int j = r + 1;

    while (true)
    {
      do
      {
        j = j - 1;
      } while (arr[j] > x);

      do
      {
        i = i + 1;
      } while (arr[i] < x);

      if (i < j)
      {
        float tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;

        Object tmp2 = s[i];
        s[i] = s[j];
        s[j] = tmp2;
      }
      else
      {
        return j;
      }
    }
  }

  private static int partition(int[] arr, int p, int r, Object[] s)
  {
    int x = arr[p];
    int i = p - 1;
    int j = r + 1;

    while (true)
    {
      do
      {
        j = j - 1;
      } while (arr[j] > x);

      do
      {
        i = i + 1;
      } while (arr[i] < x);

      if (i < j)
      {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;

        Object tmp2 = s[i];
        s[i] = s[j];
        s[j] = tmp2;
      }
      else
      {
        return j;
      }
    }
  }

  private static int partition(double[] arr, int p, int r, Object[] s)
  {
    double x = arr[p];
    int i = p - 1;
    int j = r + 1;

    while (true)
    {
      do
      {
        j = j - 1;
      } while (arr[j] > x);

      do
      {
        i = i + 1;
      } while (arr[i] < x);

      if (i < j)
      {
        double tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;

        Object tmp2 = s[i];
        s[i] = s[j];
        s[j] = tmp2;
      }
      else
      {
        return j;
      }
    }
  }

  private static int stringPartition(String[] arr, int p, int r, Object[] s)
  {
    String x = arr[p];
    int i = p - 1;
    int j = r + 1;

    while (true)
    {
      do
      {
        j = j - 1;
      } while (arr[j].compareTo(x) < 0);

      do
      {
        i = i + 1;
      } while (arr[i].compareTo(x) > 0);

      if (i < j)
      {
        String tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;

        Object tmp2 = s[i];
        s[i] = s[j];
        s[j] = tmp2;
      }
      else
      {
        return j;
      }
    }
  }
}
