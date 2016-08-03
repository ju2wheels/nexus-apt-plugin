/*
 * Sonatype Nexus (TM) Open Source Version
 * Copyright (c) 2007-2013 Sonatype, Inc.
 * All rights reserved. Includes the third-party code listed at http://links.sonatype.com/products/nexus/oss/attributions.
 *
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License Version 1.0,
 * which accompanies this distribution and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Sonatype Nexus (TM) Professional Version is available from Sonatype, Inc. "Sonatype" and "Sonatype Nexus" are trademarks
 * of Sonatype, Inc. Apache Maven is a trademark of the Apache Software Foundation. M2eclipse is a trademark of the
 * Eclipse Foundation. All other trademarks are the property of their respective owners.
 */
package com.inventage.nexusaptplugin.capabilities.signing;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.capability.support.CapabilityDescriptorSupport;
import org.sonatype.nexus.formfields.FormField;
import org.sonatype.nexus.formfields.StringTextFormField;
import org.sonatype.nexus.plugins.capabilities.CapabilityDescriptor;
import org.sonatype.nexus.plugins.capabilities.CapabilityIdentity;
import org.sonatype.nexus.plugins.capabilities.CapabilityType;
import org.sonatype.nexus.plugins.capabilities.Validator;
import org.sonatype.nexus.plugins.capabilities.support.validator.Validators;

/**
 * @since 3.0
 */
@Singleton
@Named(AptSigningCapability.TYPE_ID)
public class AptSigningCapabilityDescriptor
        extends CapabilityDescriptorSupport
        implements CapabilityDescriptor {

    private final Validators validators;

    @Inject
    public AptSigningCapabilityDescriptor(final Validators validators) {
        this.validators = validators;
    }

    @Override
    public CapabilityType type() {
        return AptSigningCapability.TYPE;
    }

    @Override
    public String name() {
        return "APT: Signing Configuration";
    }

    @Override
    public String about() {
        return "APT plugin configuration.";
    }

    @Override
    public List<FormField> formFields() {
        return Arrays.<FormField>asList(
                new StringTextFormField(
                        AptSigningCapabilityConfiguration.KEYRING,
                        "Secure keyring location",
                        "The location of the GNU PG secure keyring to be used for signing",
                        FormField.OPTIONAL
                ),
                new StringTextFormField(
                        AptSigningCapabilityConfiguration.KEY,
                        "Key ID",
                        "ID of the key in the secure keyring to be used for signing",
                        FormField.MANDATORY
                ),
                new StringTextFormField(
                        AptSigningCapabilityConfiguration.PASSPHRASE,
                        "Passphrase for the key",
                        "Passphrase for the key to be used for signing",
                        FormField.MANDATORY
                ));
    }

    @Override
    public Validator validator() {
        return validators.capability().uniquePer(AptSigningCapability.TYPE);
    }

    @Override
    public Validator validator(final CapabilityIdentity id) {
        return validator();
    }

}
