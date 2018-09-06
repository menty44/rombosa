"use strict";
var buttons_1 = require('../src/modules/options/buttons');
describe("return buttons options", function () {
    test("returns invisible buttons on false", function () {
        var opts = buttons_1.getButtonListOpts(false);
        expect(opts).toMatchObject({
            cancel: {
                visible: false
            },
            confirm: {
                visible: false
            }
        });
    });
    test("returns default obj on true", function () {
        var opts = buttons_1.getButtonListOpts(true);
        expect(opts).toMatchObject({
            cancel: {
                className: "",
                closeModal: true,
                text: "Cancel",
                value: null,
                visible: true
            },
            confirm: {
                className: "",
                closeModal: true,
                text: "OK",
                value: true,
                visible: true
            }
        });
    });
    test("returns single button on string", function () {
        var opts = buttons_1.getButtonListOpts("Test");
        expect(opts).toMatchObject({
            cancel: {
                visible: false
            },
            confirm: {
                closeModal: true,
                text: "Test",
                value: true,
                visible: true
            }
        });
    });
    test("returns two buttons on array of strings", function () {
        var opts = buttons_1.getButtonListOpts(["Annuler", "Confirmer"]);
        expect(opts).toMatchObject({
            cancel: {
                closeModal: true,
                text: "Annuler",
                value: null,
                visible: true
            },
            confirm: {
                closeModal: true,
                text: "Confirmer",
                value: true,
                visible: true
            }
        });
    });
    test("returns only cancel button when using boolean in array", function () {
        var opts = buttons_1.getButtonListOpts([true, false]);
        expect(opts).toMatchObject({
            cancel: {
                closeModal: true,
                text: "Cancel",
                value: null,
                visible: true
            },
            confirm: {
                visible: false
            }
        });
    });
});
//# sourceMappingURL=button-options.test.js.map