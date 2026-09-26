package me.serce.solidity.ide.inspections.fixes

import com.intellij.codeInspection.LocalQuickFix
import com.intellij.codeInspection.ProblemDescriptor
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiDocumentManager

class AddSpdxLicenseFix : LocalQuickFix {
  override fun getFamilyName(): String = "Add SPDX license identifier"

  override fun applyFix(project: Project, descriptor: ProblemDescriptor) {
    val file = descriptor.psiElement.containingFile ?: return
    val document = PsiDocumentManager.getInstance(project).getDocument(file) ?: return
    document.insertString(0, "// SPDX-License-Identifier: MIT\n\n")
  }
}
