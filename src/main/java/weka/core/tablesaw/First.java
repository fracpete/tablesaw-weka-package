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
 * First.java
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
 * Returns the first X rows.
 *
 * @author FracPete (fracpete at waikato dot ac dot nz)
 */
public class First
  extends AbstractTableOperation {

  private static final long serialVersionUID = 2062384379312131809L;

  /** the default number of rows. */
  public final static int DEFAULT_NUM_ROWS = 10;

  /** the number of rows. */
  protected int m_NumRows = DEFAULT_NUM_ROWS;

  /**
   * Returns a string describing this object.
   *
   * @return a description of the Loader suitable for
   * displaying in the explorer/experimenter gui
   */
  @Override
  public String globalInfo() {
    return "Returns the first X number of rows.";
  }

  /**
   * Sets the number of rows to retrieve.
   *
   * @param value	the number of rows
   */
  public void setNumRows(int value) {
    m_NumRows = value;
  }

  /**
   * Returns the number of rows to retrieve.
   *
   * @return		the number of rows
   */
  public int getNumRows() {
    return m_NumRows;
  }

  /**
   * Returns the tip text for this property
   *
   * @return tip text for this property suitable for displaying in the
   *         explorer/experimenter gui
   */
  public String numRowsTipText() {
    return "The number of rows to retrieve.";
  }

  /**
   * Returns an enumeration describing the available options.
   *
   * @return an enumeration of all the available options.
   */
  public Enumeration listOptions() {
    Vector result = new Vector();

    result.addElement(new Option("\tThe number of rows to retrieve\n"
      + "\t(default: " + DEFAULT_NUM_ROWS + ")",
      "num-rows", 1, "-num-rows <int>"));

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

    tmp = Utils.getOption("num-rows", options);
    if (!tmp.isEmpty())
      setNumRows(Integer.parseInt(tmp));
    else
      setNumRows(DEFAULT_NUM_ROWS);

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

    result.add("-num-rows");
    result.add("" + getNumRows());

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
    if (m_NumRows >= table.rowCount()) {
      System.err.println("Number of rows >= rows in table, just returning table as is!");
      return table;
    }

    return table.first(m_NumRows);
  }
}
