package org.bv.com.datastructures;

import java.util.concurrent.atomic.AtomicInteger;

public class FlagInt implements Flag {

		public static final int f00 = 0b00000000000000000000000000000000;
        public static final int f01 = 0b10000000000000000000000000000000;
        public static final int f02 = 0b01000000000000000000000000000000;
        public static final int f03 = 0b00100000000000000000000000000000;
        public static final int f04 = 0b00010000000000000000000000000000;
        public static final int f05 = 0b00001000000000000000000000000000;
        public static final int f06 = 0b00000100000000000000000000000000;
        public static final int f07 = 0b00000010000000000000000000000000;
        public static final int f08 = 0b00000001000000000000000000000000;
        public static final int f09 = 0b00000000100000000000000000000000;
        public static final int f10 = 0b00000000010000000000000000000000;
        public static final int f11 = 0b00000000001000000000000000000000;
        public static final int f12 = 0b00000000000100000000000000000000;
        public static final int f13 = 0b00000000000010000000000000000000;
        public static final int f14 = 0b00000000000001000000000000000000;
        public static final int f15 = 0b00000000000000100000000000000000;
        public static final int f16 = 0b00000000000000010000000000000000;
        public static final int f17 = 0b00000000000000001000000000000000;
        public static final int f18 = 0b00000000000000000100000000000000;
        public static final int f19 = 0b00000000000000000010000000000000;
        public static final int f20 = 0b00000000000000000001000000000000;
        public static final int f21 = 0b00000000000000000000100000000000;
        public static final int f22 = 0b00000000000000000000010000000000;
        public static final int f23 = 0b00000000000000000000001000000000;
        public static final int f24 = 0b00000000000000000000000100000000;
        public static final int f25 = 0b00000000000000000000000010000000;
        public static final int f26 = 0b00000000000000000000000001000000;
        public static final int f27 = 0b00000000000000000000000000100000;
        public static final int f28 = 0b00000000000000000000000000010000;
        public static final int f29 = 0b00000000000000000000000000001000;
        public static final int f30 = 0b00000000000000000000000000000100;
        public static final int f31 = 0b00000000000000000000000000000010;
        public static final int f32 = 0b00000000000000000000000000000001;
        public static final int fXX = 0b11111111111111111111111111111111;

        private final AtomicInteger flags;

        /**
         * Access to the value
         *
         * @return value of the flag.
         */
        @Override
		public int value() {
        	return flags.intValue();
        }

        /**
         * Default CTOR initializes all flags off
         */
        public FlagInt() {
        	flags = new AtomicInteger(f00);
        }
        /*-------------------------------------------------------------------*/
        /**
         * CTOR with custom flag initialization.
         *
         * @param flagsToSet the initial value of the flags.
         */
        public FlagInt(final int flagsToSet) { flags = new AtomicInteger(flagsToSet); }
        /*-------------------------------------------------------------------*/
        /**
         * Check if individual flags are true.
         * @param setting bit mask of flags to check
         * @return true if all flags specified are on.
         */
        @Override
		public boolean isOn(final int setting) { return (setting & flags.intValue())  == setting; }
        /*-------------------------------------------------------------------*/
        /**
         * Check to see if individual flags are set off.
         * @param setting a bit mask of the flags to check
         * @return true if all the in the bit mask are off
         */
        @Override
		public boolean isOff(final int setting) { return (setting & flags.intValue()) == 0; }
        /*-------------------------------------------------------------------*/
        /**
         * Set individual bits on
         *
         * @param setting the bits to set on.
         */
        @Override
		public void setOn(final int setting) {
            int current, found, newValue;
            do {
                current = flags.intValue();
                newValue = current | setting;
                found = flags.compareAndExchange( current, newValue);
            } while (found != current);
        }
        /*-------------------------------------------------------------------*/
        /**
         * Set individual bits off
         *
         * @param setting a bit mask of the bits to turn off.
         */
        @Override
		public void setOff(final int setting) {
            int current, found, newValue;
            do {
                current = flags.intValue();
                newValue = current ^ setting;
                found = flags.compareAndExchange(current, newValue);
            } while (found != current);
        }
        /*-------------------------------------------------------------------*/
        /**
         * Turn all flags off
         */
        @Override
		public void clearAll() { flags.set(f00); }
}
