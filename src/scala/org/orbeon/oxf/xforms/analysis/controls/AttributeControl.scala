/**
 *  Copyright (C) 2010 Orbeon, Inc.
 *
 *  This program is free software; you can redistribute it and/or modify it under the terms of the
 *  GNU Lesser General Public License as published by the Free Software Foundation; either version
 *  2.1 of the License, or (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 *  without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 *  See the GNU Lesser General Public License for more details.
 *
 *  The full text of the license is available at http://www.gnu.org/copyleft/lesser.html
 */
package org.orbeon.oxf.xforms.analysis.controls

import org.dom4j.Element
import org.orbeon.oxf.xforms.analysis._
import org.orbeon.oxf.xforms.xbl.XBLBindings
import org.orbeon.oxf.xforms.XFormsConstants


class AttributeControl(staticStateContext: StaticStateContext, element: Element, parent: ContainerTrait, preceding: Option[ElementAnalysis], scope: XBLBindings#Scope)
        extends CoreControl(staticStateContext, element, parent, preceding, scope)
        with ValueTrait {

    // Attribute control uses an AVT
    // TODO: Add support for AVT
    override def computeValueAnalysis = Some(NegativeAnalysis(value.get)) // we must have a value

    val forPrefixedId = scope.getFullPrefix + element.attributeValue(XFormsConstants.FOR_QNAME)
    val name = element.attributeValue(XFormsConstants.NAME_QNAME)
}