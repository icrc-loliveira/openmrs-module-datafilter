/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.datafilter;

import static org.openmrs.module.datafilter.DataFilterConstants.LOCATION_BASED_FILTER_NAME_ENCOUNTER;
import static org.openmrs.module.datafilter.DataFilterConstants.LOCATION_BASED_FILTER_NAME_OBS;
import static org.openmrs.module.datafilter.DataFilterConstants.LOCATION_BASED_FILTER_NAME_PATIENT;
import static org.openmrs.module.datafilter.DataFilterConstants.LOCATION_BASED_FILTER_NAME_VISIT;

import org.openmrs.Privilege;
import org.openmrs.Role;
import org.openmrs.api.AdministrationService;
import org.openmrs.api.context.Context;
import org.openmrs.util.PrivilegeConstants;

public class DataFilterTestUtils {
	
	public static void disableLocationFiltering() {
		AdministrationService as = Context.getAdministrationService();
		as.setGlobalProperty(LOCATION_BASED_FILTER_NAME_PATIENT + "_" + DataFilterConstants.DISABLED, "true");
		as.setGlobalProperty(LOCATION_BASED_FILTER_NAME_VISIT + "_" + DataFilterConstants.DISABLED, "true");
		as.setGlobalProperty(LOCATION_BASED_FILTER_NAME_ENCOUNTER + "_" + DataFilterConstants.DISABLED, "true");
		as.setGlobalProperty(LOCATION_BASED_FILTER_NAME_OBS + "_" + DataFilterConstants.DISABLED, "true");
		Context.flushSession();
	}
	
	public static void addPrivilege(String privilege) {
		Context.addProxyPrivilege(PrivilegeConstants.MANAGE_ROLES);
		try {
			Privilege p = Context.getUserService().getPrivilege(privilege);
			Role role = Context.getAuthenticatedUser().getRoles().iterator().next();
			role.addPrivilege(p);
			Context.getUserService().saveRole(role);
			Context.flushSession();
		}
		finally {
			Context.removeProxyPrivilege(PrivilegeConstants.MANAGE_ROLES);
		}
	}
	
}
