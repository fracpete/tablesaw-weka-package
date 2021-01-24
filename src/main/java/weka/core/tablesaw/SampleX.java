/*
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

/*
 * SampleX.java
 * Copyright (C) 2021 University of Waikato, Hamilton, NZ
 */

package weka.core.tablesaw;

import tech.tablesaw.api.Table;
import weka.core.Option;
import weka.core.Utils;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Vector;

/**
 * Returns a sub-sample of specified proportion.
 *
 * @author FracPete (fracpete at waikato dot ac dot nz)
 */
public class SampleX
  extends AbstractTableOperation {

  private static final long serialVersionUID = 2062384379312131809L;

  /** the default size. */
  public final static double DEFAULT_SIZE = 1.0;

  /** the size. */
  protected double m_Size = DEFAULT_SIZE;

  /**
   * Returns a string describing this object.
   *
   * @return a description of the Loader suitable for
   * displaying in the explorer/experimenter gui
   */
  @Override
  public String globalInfo() {
    return "Returns a sub-sample of specified proportion.";
  }

  /**
   * Sets the sample proportion (0-1).
   *
   * @param value	the sample size &lt;=0 for all
   */
  public void setSize(double value) {
    if (value <= 0)
      value = -1;
    if (value > 1.0)
      value = 1.0;
    m_Size = value;
  }

  /**
   * Returns the sample proportion (0-1).
   *
   * @return		the sample size &lt;=0 for all
   */
  public double getSize() {
    return m_Size;
  }

  /**
   * Returns the tip text for this property
   *
   * @return tip text for this property suitable for displaying in the
   *         explorer/experimenter gui
   */
  public String sizeTipText() {
    return "The proportion of the sample to return.";
  }

  /**
   * Returns an enumeration describing the available options.
   *
   * @return an enumeration of all the available options.
   */
  public Enumeration listOptions() {
    Vector result = new Vector();

    result.addElement(new Option("\tThe proportion of the dataset to return as sample (0-1)\n"
      + "\t(default: " + DEFAULT_SIZE + ")",
      "size", 1, "-size <double>"));

    return result.elements();
  }

  /**
   * Parses a given list of options.
   *
   *
   * @param options the list of options as an array of strings
   * @throws Exception if an option is not supported
   */
  public void setOptions(String[] options) throws Exception {
    String 	tmp;

    tmp = Utils.getOption("size", options);
    if (!tmp.isEmpty())
      setSize(Double.parseDouble(tmp));
    else
      setSize(DEFAULT_SIZE);

    Utils.checkForRemainingOptions(options);
  }

  /**
   * Gets the current settings of the Apriori object.
   *
   * @return an array of strings suitable for passing to setOptions
   */
  public String[] getOptions() {
    List<String> result;

    result = new ArrayList<String>();

    result.add("-size");
    result.add("" + getSize());

    return result.toArray(new String[0]);
  }

  /**
   * Processes the table.
   *
   * @param table the table to work on
   * @return the updated table
   */
  @Override
  public Table processTable(Table table) {
    if (m_Size <= 0)
      return table;

    return table.sampleX(m_Size);
  }
}
