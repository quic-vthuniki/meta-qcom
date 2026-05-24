inherit autotools
require include/common-location-defines.inc
require include/location-license-bsd3-clause.inc

DESCRIPTION = "Location client api hdr"

SRCPROJECT = "git://git.codelinaro.org/clo/le/platform/vendor/qcom-opensource/location.git;protocol=https"
SRCBRANCH  = "location.lnx.0.0"
SRCREV     = "ca299d1a2ce6ccff2cd528c6d49042a794160918"

SRC_URI = "${SRCPROJECT};branch=${SRCBRANCH};destsuffix=qcom-opensource/location"
S = "${UNPACKDIR}/qcom-opensource/location/client_api/inc"

do_configure[noexec] = "1"
do_compile[noexec] = "1"

do_install() {
    install -d ${D}${includedir}
    install -m 644 ${S}/*.h ${D}${includedir}
}
