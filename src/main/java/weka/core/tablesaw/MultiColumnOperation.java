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
 * MultiColumnOperation.java
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
 * Applies multiple column operations.
 *
 * @author FracPete (fracpete at waikato dot ac dot nz)
 */
public class MultiColumnOperation
  extends AbstractColumnOperation {

  private static final long serialVersionUID = 2062384379312131809L;

  /** the operations. */
  protected ColumnOperation[] m_Operations = new ColumnOperation[0];

  /**
   * Returns a string describing this object.
   *
   * @return a description of the Loader suitable for
   * displaying in the explorer/experimenter gui
   */
  @Override
  public String globalInfo() {
    return "Applies the specified column operations.";
  }

  /**
   * Sets the operations.
   *
   * @param value	the operations
   */
  public void setOperations(ColumnOperation[] value) {
    m_Operations = value;
  }

  /**
   * Returns the operations.
   *
   * @return		the operations
   */
  public ColumnOperation[] getOperations() {
    return m_Operations;
  }

  /**
   * Returns the tip text for this property
   *
   * @return tip text for this property suitable for displaying in the
   *         explorer/experimenter gui
   */
  public String operationsTipText() {
    return "The column operations to apply sequentially.";
  }

  /**
   * Returns an enumeration describing the available options.
   *
   * @return an enumeration of all the available options.
   */
  public Enumeration listOptions() {
    Vector result = new Vector();

    result.addElement(new Option("\tThe column operations to apply sequentially.\n"
      + "\tCan be specified multiple times.\n"
      + "\t(default: none)",
      "operation", 1, "-operation <classname + options>"));

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
    String 		tmp;
    String		classname;
    String[]		tmpOptions;
    List<ColumnOperation> 	ops;

    ops = new ArrayList<>();
    while (!(tmp = Utils.getOption("operation", options)).isEmpty()) {
      tmpOptions    = Utils.splitOptions(tmp);
      classname     = tmpOptions[0];
      tmpOptions[0] = "";
      ops.add((ColumnOperation) Utils.forName(ColumnOperation.class, classname, tmpOptions));
    }
    setOperations(ops.toArray(new ColumnOperation[0]));

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

    for (ColumnOperation op: getOperations()) {
      result.add("-operation");
      result.add(Utils.toCommandLine(op));
    }

    return result.toArray(new String[0]);
  }

  /**
   * Processes the columns.
   *
   * @param table the table to work on
   * @return the updated table
   */
  @Override
  public Table processColumns(Table table) {
    Table	result;
    int		i;

    result = table;

    for (i = 0; i < m_Operations.length; i++)
      result = m_Operations[i].processColumns(result);

    return result;
  }
}
