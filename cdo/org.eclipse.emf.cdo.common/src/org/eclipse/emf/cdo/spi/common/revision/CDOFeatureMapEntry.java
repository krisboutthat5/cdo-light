/*
 * Copyright (c) 2004 - 2012 Eike Stepper (Berlin, Germany) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Martin Taal - derived from CDOFeatureMapEntryImpl
 */
package org.eclipse.emf.cdo.spi.common.revision;

import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.FeatureMap;

/**
 * @since 3.0
 * @author Martin Taal
 */
public interface CDOFeatureMapEntry extends FeatureMap.Entry
{
  void setEStructuralFeature(EStructuralFeature feature);

  void setValue(Object value);
}
