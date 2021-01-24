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
 * CountBy.java
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
 * Generates a table with two columns, the first with the name of the categorical value and the second with the count for that value.
 *
 * @author FracPete (fracpete at waikato dot ac dot nz)
 */
public class CountBy
  extends AbstractTableOperation {

  private static final long serialVersionUID = 2062384379312131809L;

  /** the default column. */
  public final static String DEFAULT_COLUMN = "";

  /** the column. */
  protected String m_Column = DEFAULT_COLUMN;

  /**
   * Returns a string describing this object.
   *
   * @return a description of the Loader suitable for
   * displaying in the explorer/experimenter gui
   */
  @Override
  public String globalInfo() {
    return "Generates a table with two columns, first the name of the categorical value and the second with the count for that value.";
  }

  /**
   * Sets the categorical column to perform the count on.
   *
   * @param value	the column name
   */
  public void setColumn(String value) {
    m_Column = value;
  }

  /**
   * Returns the categorical column to perform the count on.
   *
   * @return		the column name
   */
  public String getColumn() {
    return m_Column;
  }

  /**
   * Returns the tip text for this property
   *
   * @return tip text for this property suitable for displaying in the
   *         explorer/experimenter gui
   */
  public String columnTipText() {
    return "The name of categorical column to perform the count on.";
  }

  /**
   * Returns an enumeration describing the available options.
   *
   * @return an enumeration of all the available options.
   */
  public Enumeration listOptions() {
    Vector result = new Vector();

    result.addElement(new Option("\tThe categorical column to perform the count on\n"
      + "\t(default: " + DEFAULT_COLUMN + ")",
      "column", 1, "-column <name>"));

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

    tmp = Utils.getOption("column", options);
    if (!tmp.isEmpty())
      setColumn(tmp);
    else
      setColumn(DEFAULT_COLUMN);

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

    result.add("-column");
    result.add("" + getColumn());

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
    return table.countBy(m_Column);
  }
}
