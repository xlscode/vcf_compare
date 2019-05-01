package org.lscode.vcf_compare;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WrappedVCardsTest {

    @Test
    void containsPhoneNumber(){
        final String vct1 = "BEGIN:VCARD\n" +
                "VERSION:2.1\n" +
                "TEL;VOICE:224-284-848\n" +
                "TEL;VOICE:801-009-729\n" +
                "N:House;Gregory;;Dr;MD\r\n" +
                "FN:Dr. Gregory House M.D.\r\n" +
                "ORG:Uncaria\n" +
                "END:VCARD";

        final String vct2 = "BEGIN:VCARD\n" +
                "VERSION:2.1\n" +
                "TEL;VOICE:555-276-987\n" +
                "TEL;VOICE:334-006-729\n" +
                "N:Scully;Dana;;Agent;\r\n" +
                "FN:Agent Dana Scully\r\n" +
                "ORG:FBI\n" +
                "END:VCARD";

        WrappedVCards allCards = new WrappedVCards("48");

        allCards.add(new WrappedVCard(vct1, "48"));
        allCards.add(new WrappedVCard(vct2, "48"));

        assertTrue(allCards.size() == 2);

        assertTrue(allCards.containsPhoneNumber("224-284-848"));
        assertTrue(allCards.containsPhoneNumber("334-006-729"));
        assertFalse(allCards.containsPhoneNumber("334-006-720"));


    }
}