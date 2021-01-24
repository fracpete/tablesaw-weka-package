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
 * Sort.java
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
 * Sorts the data.
 *
 * @author FracPete (fracpete at waikato dot ac dot nz)
 */
public class Sort
  extends AbstractTableOperation {

  private static final long serialVersionUID = 2062384379312131809L;

  /** the default column list. */
  public final static String DEFAULT_COLUMNS = "";

  /** the column list (comma-separated). */
  protected String m_Columns = DEFAULT_COLUMNS;

  /** whether to sort in descending order. */
  protected boolean m_Descending = false;

  /**
   * Returns a string describing this object.
   *
   * @return a description of the Loader suitable for
   * displaying in the explorer/experimenter gui
   */
  @Override
  public String globalInfo() {
    return "Sorts the data using the specified columns.";
  }

  /**
   * Sets the column list.
   *
   * @param value	the columns (comma-separated names)
   */
  public void setColumns(String value) {
    m_Columns = value;
  }

  /**
   * Returns the column list.
   *
   * @return		the columns (comma-separated names)
   */
  public String getColumns() {
    return m_Columns;
  }

  /**
   * Returns the tip text for this property
   *
   * @return tip text for this property suitable for displaying in the
   *         explorer/experimenter gui
   */
  public String columnsTipText() {
    return "The columns to sort on (comma-separated names).";
  }

  /**
   * Sets whether to sort in descending fashion.
   *
   * @param value	true if descending
   */
  public void setDescending(boolean value) {
    m_Descending = value;
  }

  /**
   * Returns whether to sort in descending fashion.
   *
   * @return		true if descending
   */
  public boolean getDescending() {
    return m_Descending;
  }

  /**
   * Returns the tip text for this property
   *
   * @return tip text for this property suitable for displaying in the
   *         explorer/experimenter gui
   */
  public String descendingTipText() {
    return "If enabled, sorting is done in descending fashion.";
  }

  /**
   * Returns an enumeration describing the available options.
   *
   * @return an enumeration of all the available options.
   */
  public Enumeration listOptions() {
    Vector result = new Vector();

    result.addElement(new Option("\tThe list of columns to sort on (comma-separated names)\n"
      + "\t(default: " + DEFAULT_COLUMNS + ")",
      "columns", 1, "-columns <name1,[name2[,...]]>"));

    result.addElement(new Option("\tChanges sorting from ascending to descending\n"
      + "\t(default: ascending)",
      "descending", 0, "-descending"));

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

    tmp = Utils.getOption("columns", options);
    if (!tmp.isEmpty())
      setColumns(tmp);
    else
      setColumns(DEFAULT_COLUMNS);

    setDescending(Utils.getFlag("descending", options));

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

    result.add("-columns");
    result.add("" + getColumns());

    if (getDescending())
      result.add("-descending");

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
    String[]	columns;

    columns = m_Columns.split(",");

    if (m_Descending)
      return table.sortDescendingOn(columns);
    else
      return table.sortAscendingOn(columns);
  }
}
