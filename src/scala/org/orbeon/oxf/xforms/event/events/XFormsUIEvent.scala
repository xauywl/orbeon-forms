/**
 * Copyright (C) 2010 Orbeon, Inc.
 *
 * This program is free software; you can redistribute it and/or modify it under the terms of the
 * GNU Lesser General Public License as published by the Free Software Foundation; either version
 * 2.1 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 * The full text of the license is available at http://www.gnu.org/copyleft/lesser.html
 */
package org.orbeon.oxf.xforms.event.events

import org.orbeon.oxf.xforms.control.XFormsControl
import org.orbeon.oxf.xforms.event.XFormsEvent
import XFormsEvent._

/**
 * Base class for UI events, that is events only dispatched to controls.
 */
abstract class XFormsUIEvent(
        eventName: String,
        val targetControl: XFormsControl,
        properties: PropertyGetter,
        bubbles: Boolean,
        cancelable: Boolean)
    extends XFormsEvent(
        eventName,
        targetControl,
        properties,
        bubbles,
        cancelable) {

    def this(eventName: String, target: XFormsControl, properties: PropertyGetter) =
        this(eventName, target, properties, bubbles = true, cancelable = false)

    require(targetControl ne null)

    override def lazyProperties = getters(this, XFormsUIEvent.Getters)
    override def newPropertyName(name: String) = XFormsUIEvent.Deprecated.get(name) orElse super.newPropertyName(name)
}

private object XFormsUIEvent {
    
    val Deprecated = Map(
        "target-ref" → "xxf:binding",
        "alert"      → "xxf:alert",
        "label"      → "xxf:label",
        "hint"       → "xxf:hint",
        "help"       → "xxf:help"
    )
    
    val Getters = Map[String, XFormsUIEvent ⇒ Option[Any]](
        "target-ref"                    → binding,
        xxformsName("binding")          → binding,
        xxformsName("control-position") → controlPosition,
        "label"                         → label,
        xxformsName("label")            → label,
        "help"                          → help,
        xxformsName("help")             → help,
        "hint"                          → hint,
        xxformsName("hint")             → hint,
        "alert"                         → alert,
        xxformsName("alert")            → alert
    )

    def binding(e: XFormsUIEvent) = Option(e.targetControl.binding)

    def controlPosition(e: XFormsUIEvent) = {
        val controlStaticPosition = e.targetControl.container.getPartAnalysis.getControlPosition(e.targetControl.getPrefixedId)
        if (controlStaticPosition >= 0) Some(controlStaticPosition) else None
    }

    def label(e: XFormsUIEvent) = Option(e.targetControl.getLabel)
    def help(e: XFormsUIEvent)  = Option(e.targetControl.getHelp)
    def hint(e: XFormsUIEvent)  = Option(e.targetControl.getHint)
    def alert(e: XFormsUIEvent) = Option(e.targetControl.getAlert)
}