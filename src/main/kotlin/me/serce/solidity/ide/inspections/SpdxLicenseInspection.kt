package me.serce.solidity.ide.inspections

import com.intellij.codeInspection.LocalInspectionTool
import com.intellij.codeInspection.ProblemHighlightType
import com.intellij.codeInspection.ProblemsHolder
import com.intellij.psi.PsiComment
import com.intellij.psi.PsiElementVisitor
import com.intellij.psi.PsiFile
import com.intellij.psi.util.PsiTreeUtil
import me.serce.solidity.ide.inspections.fixes.AddSpdxLicenseFix
import me.serce.solidity.lang.psi.SolPragmaDirective
import me.serce.solidity.lang.psi.SolVisitor

class SpdxLicenseInspection : LocalInspectionTool() {
  override fun getDisplayName(): String = ""

  override fun buildVisitor(holder: ProblemsHolder, isOnTheFly: Boolean): PsiElementVisitor {
    return object : SolVisitor() {
      override fun visitPragmaDirective(directive: SolPragmaDirective) {
        val file = directive.containingFile ?: return
        if (PsiTreeUtil.findChildOfType(file, SolPragmaDirective::class.java) != directive) {
          return
        }
        if (hasSpdxIdentifier(file)) {
          return
        }
        holder.registerProblem(
          directive,
          "Missing SPDX license identifier",
          ProblemHighlightType.WEAK_WARNING,
          AddSpdxLicenseFix()
        )
      }
    }
  }

  companion object {
    private const val SPDX_MARKER = "SPDX-License-Identifier:"

    fun hasSpdxIdentifier(file: PsiFile): Boolean =
      PsiTreeUtil.findChildrenOfType(file, PsiComment::class.java).any { it.text.contains(SPDX_MARKER) }
  }
}
