SUMMARY = "Qcom package group for Location modules"
LICENSE = "BSD-3-Clause-Clear"

inherit packagegroup

# location packages which are common across various machines
LOCATION_PACKAGES = "\
    location-client-api-hdr \
"
RDEPENDS:${PN} = " ${LOCATION_PACKAGES} "

