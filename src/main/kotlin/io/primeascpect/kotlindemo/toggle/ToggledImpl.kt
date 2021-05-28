package io.primeascpect.kotlindemo.toggle

import org.springframework.stereotype.Component

@Component
class ToggledImpl(toggle: Toggle, enabledImpl: DemoInterface, disabledImpl: DemoInterface): DemoInterface by (if (toggle.isEnabled()) enabledImpl else disabledImpl)