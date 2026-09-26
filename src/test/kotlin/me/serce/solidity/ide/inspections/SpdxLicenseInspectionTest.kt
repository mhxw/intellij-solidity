package me.serce.solidity.ide.inspections

class SpdxLicenseInspectionTest : SolInspectionsTestBase(SpdxLicenseInspection()) {
  fun testMissingSpdxLicense() = checkByText(
    """
        /*@weak_warning descr="Missing SPDX license identifier"@*/pragma solidity ^0.8.0;/*@/weak_warning@*/
        contract A {}
    """.trimIndent(),
    checkWeakWarn = true
  )

  fun testSpdxLicenseInLineComment() = checkByText(
    """
        // SPDX-License-Identifier: MIT
        pragma solidity ^0.8.0;
        contract A {}
    """.trimIndent(),
    checkWeakWarn = true
  )

  fun testSpdxLicenseInBlockComment() = checkByText(
    """
        /* SPDX-License-Identifier: MIT */
        pragma solidity ^0.8.0;
        contract A {}
    """.trimIndent(),
    checkWeakWarn = true
  )

  fun testAddSpdxLicenseFix() {
    myFixture.configureByText("main.sol", "pragma solidity ^0.8.0;\ncontract A {}")
    enableInspection()
    applyQuickFix("Add SPDX license identifier")
    myFixture.checkResult("// SPDX-License-Identifier: MIT\n\npragma solidity ^0.8.0;\ncontract A {}")
  }
}
