package com.guardiansuite.child

import android.accessibilityservice.AccessibilityService
import android.view.accessibility.AccessibilityEvent

class GuardianAccessibilityService : AccessibilityService() {
	override fun onAccessibilityEvent(event: AccessibilityEvent?) {
		// TODO: Observe foreground app changes and enforce block rules
	}

	override fun onInterrupt() {
		// Required override
	}
}