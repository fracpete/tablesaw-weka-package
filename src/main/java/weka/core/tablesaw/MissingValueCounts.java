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
 * MissingValueCounts.java
 * Copyright (C) 2021 University of Waikato, Hamilton, NZ
 */

package weka.core.tablesaw;

import tech.tablesaw.api.Table;

/**
 * Returns counts of missing values.
 *
 * @author FracPete (fracpete at waikato dot ac dot nz)
 */
public class MissingValueCounts
  extends AbstractTableOperation {

  private static final long serialVersionUID = 2062384379312131809L;

  /**
   * Returns a string describing this object.
   *
   * @return a description of the Loader suitable for
   * displaying in the explorer/experimenter gui
   */
  @Override
  public String globalInfo() {
    return "Returns counts of missing values (in single row).";
  }

  /**
   * Processes the table.
   *
   * @param table the table to work on
   * @return the updated table
   */
  @Override
  public Table processTable(Table table) {
    return table.missingValueCounts();
  }
}
