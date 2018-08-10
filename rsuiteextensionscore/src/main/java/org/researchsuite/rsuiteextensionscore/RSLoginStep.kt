package org.researchsuite.rsuiteextensionscore

import android.text.InputType
import org.researchstack.backbone.step.Step

open class RSLoginStep @JvmOverloads constructor(
        identifier: String,
        text: String?,
        title: String?,
        logInLayoutClass: Class<*>,
        val loginButtonTitle: String = "Login",
        val forgotPasswordButtonTitle: String? = null,
        val identityFieldName: String = "Username",
        val identityFieldInputType: Int = InputType.TYPE_CLASS_TEXT,
        val showIdentityField: Boolean = true,
        val passwordFieldName: String = "Password",
        val passwordFieldInputType: Int = (InputType.TYPE_CLASS_TEXT and InputType.TYPE_TEXT_VARIATION_PASSWORD),
        val showPasswordField: Boolean = true
): Step(identifier) {

    init {
        this.text = text
        this.title = title
        this.stepLayoutClass = logInLayoutClass
    }

}