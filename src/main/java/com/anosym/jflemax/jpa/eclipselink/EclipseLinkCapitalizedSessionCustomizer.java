/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anosym.jflemax.jpa.eclipselink;

import com.anosym.utilities.Utility;
import java.sql.SQLException;
import org.eclipse.persistence.config.SessionCustomizer;
import org.eclipse.persistence.descriptors.ClassDescriptor;
import org.eclipse.persistence.sessions.Session;
import org.eclipse.persistence.tools.schemaframework.IndexDefinition;

/**
 *
 * @author marembo
 */
public class EclipseLinkCapitalizedSessionCustomizer implements SessionCustomizer {

  @Override
  public void customize(Session session) throws SQLException {
    for (ClassDescriptor descriptor : session.getDescriptors().values()) {
      //Only change the table name for non-embedable entities with no @Table already
      if (!descriptor.getTables().isEmpty() && descriptor.getAlias().equalsIgnoreCase(descriptor.getTableName())) {
        String tableName = Utility.capitalize(descriptor.getTableName());
        descriptor.setTableName(tableName);
        for (IndexDefinition index : descriptor.getTables().get(0).getIndexes()) {
          index.setTargetTable(tableName);
        }
      }
    }
  }
}
