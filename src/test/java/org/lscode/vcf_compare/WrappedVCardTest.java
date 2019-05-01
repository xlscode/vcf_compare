package org.lscode.vcf_compare;

import static org.junit.jupiter.api.Assertions.*;

class WrappedVCardTest {

    @org.junit.jupiter.api.Test
    void containsPhoneNumber() {
        final String vct = "BEGIN:VCARD\n" +
                "VERSION:2.1\n" +
                "TEL;VOICE:224-284-848\n" +
                "TEL;VOICE:801-009-729\n" +
                "N:House;Gregory;;Dr;MD\r\n" +
                "FN:Dr. Gregory House M.D.\r\n" +
                "ORG:Uncaria\n" +
                "END:VCARD";

        WrappedVCard wcard = new WrappedVCard(vct, "48");

        assertTrue(wcard.containsPhoneNumber("224-284-848"));
        assertTrue(wcard.containsPhoneNumber("+48 224-284-848"));
        assertTrue(wcard.containsPhoneNumber("+48 224-284-848p"));
        assertTrue(wcard.containsPhoneNumber("224284848"));
        assertTrue(wcard.containsPhoneNumber("+48x224x284x848p"));
        assertFalse(wcard.containsPhoneNumber("+49 224-284-848"));
        assertFalse(wcard.containsPhoneNumber("+48 224-284-8489"));

        assertTrue(wcard.containsPhoneNumber("801-009-729"));
        assertTrue(wcard.containsPhoneNumber("+48 801-009-729"));
        assertTrue(wcard.containsPhoneNumber("801-009-729p"));
        assertTrue(wcard.containsPhoneNumber("801009729"));
        assertTrue(wcard.containsPhoneNumber("801x009x729"));
        assertFalse(wcard.containsPhoneNumber("801-009-7299"));
        assertFalse(wcard.containsPhoneNumber("802-009-729"));

    }

    @org.junit.jupiter.api.Test
    void phoneNumbers() {
        assertTrue(true);
    }
}