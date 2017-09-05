package com.udemy.converter;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class TestCrypt {

	public static void main(String[] args) {
		BCryptPasswordEncoder pe = new BCryptPasswordEncoder();
		System.out.println(pe.encode("admin"));
	}
}
/*
$2a$10$4udbGRVCPQAPHBGpl7ont.5vH2mna1.NCq2bozraXlbC.e0WDM2Iu
$2a$10$OsIgM5c7d67EbrvdnsrC3.o43bbCLgl3kMmD66WQGNnbd5kLe4pB2
$2a$10$1Qfo0.rL3YZPfWPIMGt.Semn/5DQoXXcVcsXuisAwNVtAY5u3Pcl.
$2a$10$t33dAsoWufUiI4S5PDmfi.JpA2rihHL7Z3mr6XfSzKiBo.5hxXYra
*/