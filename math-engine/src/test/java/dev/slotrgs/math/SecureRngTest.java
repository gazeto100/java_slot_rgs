package dev.slotrgs.math;

class SecureRngTest extends RngContractTest {

    @Override
    Rng createRng() {
        return new SecureRng();
    }
}
