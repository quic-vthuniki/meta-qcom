inherit autotools pkgconfig useradd
require include/common-location-defines.inc

DESCRIPTION = "GPS Location HAL"

LICENSE = "BSD-3-Clause-Clear"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=2998c54c288b081076c9af987bdf4838"

SRC_URI = "git://github.com/quic-vthuniki/location-hal-qcom.git;protocol=https;branch=merge_recp_test1;tag=v${PV}"
SRCREV  = "9f9f1748475c45864eee1fc88d363ec79be044b6"

S = "${UNPACKDIR}/${BP}"

DEPENDS = "glib-2.0 sqlite3 libxml2"

USERADD_PACKAGES = "${PN}"
USERADD_PARAM:${PN} = "--no-create-home --user-group locclient; \
                       --groups locclient --no-create-home --user-group gps; \
"

do_rm_work[noexec] = "1"

do_install:append() {
    #Install default gps.conf file
    install -m 0644 -D ${S}/etc/gps.conf ${D}${sysconfdir}/gps.conf
    ## Ownership to gps:gps for gps.conf
    chown gps:gps ${D}${sysconfdir}/gps.conf
}
