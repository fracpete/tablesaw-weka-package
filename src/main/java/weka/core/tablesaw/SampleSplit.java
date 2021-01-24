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
 * SampleSplit.java
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
 * Splits the data randomly in two and returns either the first or second.
 *
 * @author FracPete (fracpete at waikato dot ac dot nz)
 */
public class SampleSplit
  extends AbstractTableOperation {

  private static final long serialVersionUID = 2062384379312131809L;

  /** the default proportion. */
  public final static double DEFAULT_PROPORTION = 0.5;

  /** the split proportion. */
  protected double m_Proportion = DEFAULT_PROPORTION;

  /** whether to return the second part. */
  protected boolean m_Second = false;

  /**
   * Returns a string describing this object.
   *
   * @return a description of the Loader suitable for
   * displaying in the explorer/experimenter gui
   */
  @Override
  public String globalInfo() {
    return "Splits the data randomly in two and returns either the first or second.";
  }

  /**
   * Sets the split proportion.
   *
   * @param value	the proportion (0-1)
   */
  public void setProportion(double value) {
    if ((value > 0) && (value < 1))
      m_Proportion = value;
    else
      System.err.println("Proportion must satisfy: 0 < x < 1, provided: " + value);
  }

  /**
   * Returns the proportion.
   *
   * @return		the proportion (0-1)
   */
  public double getProportion() {
    return m_Proportion;
  }

  /**
   * Returns the tip text for this property
   *
   * @return tip text for this property suitable for displaying in the
   *         explorer/experimenter gui
   */
  public String proportionTipText() {
    return "The split proportion (0 < x < 1).";
  }

  /**
   * Sets whether to return the first or second part.
   *
   * @param value	true if to return second
   */
  public void setSecond(boolean value) {
    m_Second = value;
  }

  /**
   * Returns whether to return the first or second part.
   *
   * @return		true if descending
   */
  public boolean getSecond() {
    return m_Second;
  }

  /**
   * Returns the tip text for this property
   *
   * @return tip text for this property suitable for displaying in the
   *         explorer/experimenter gui
   */
  public String secondTipText() {
    return "If enabled, the second part gets returned.";
  }

  /**
   * Returns an enumeration describing the available options.
   *
   * @return an enumeration of all the available options.
   */
  public Enumeration listOptions() {
    Vector result = new Vector();

    result.addElement(new Option("\tThe split proportion\n"
      + "\t(default: " + DEFAULT_PROPORTION + ")",
      "proportion", 1, "-proportion <0-1>"));

    result.addElement(new Option("\tFor returning the second part\n"
      + "\t(default: first)",
      "second", 0, "-second"));

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

    tmp = Utils.getOption("proportion", options);
    if (!tmp.isEmpty())
      setProportion(Double.parseDouble(tmp));
    else
      setProportion(DEFAULT_PROPORTION);

    setSecond(Utils.getFlag("second", options));

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

    result.add("-proportion");
    result.add("" + getProportion());

    if (getSecond())
      result.add("-second");

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
    if (m_Second)
      return table.sampleSplit(m_Proportion)[1];
    else
      return table.sampleSplit(m_Proportion)[0];
  }
}
