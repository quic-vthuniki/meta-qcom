SUMMARY = "Qcom package group for Location modules"
LICENSE = "BSD-3-Clause-Clear"

#inherit packagegroup

ALLOW_EMPTY:${PN} = "1"

# location packages which are common across various machines
LOCATION_PACKAGES = " location-apis \
"

RDEPENDS:${PN} = " ${LOCATION_PACKAGES} "

