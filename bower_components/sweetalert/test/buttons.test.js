"use strict";
var __awaiter = (this && this.__awaiter) || function (thisArg, _arguments, P, generator) {
    return new (P || (P = Promise))(function (resolve, reject) {
        function fulfilled(value) { try { step(generator.next(value)); } catch (e) { reject(e); } }
        function rejected(value) { try { step(generator.throw(value)); } catch (e) { reject(e); } }
        function step(result) { result.done ? resolve(result.value) : new P(function (resolve) { resolve(result.value); }).then(fulfilled, rejected); }
        step((generator = generator.apply(thisArg, _arguments)).next());
    });
};
var utils_1 = require('./utils');
var BUTTON = utils_1.CLASS_NAMES.BUTTON, CONFIRM_BUTTON = utils_1.CLASS_NAMES.CONFIRM_BUTTON, CANCEL_BUTTON = utils_1.CLASS_NAMES.CANCEL_BUTTON, MODAL = utils_1.CLASS_NAMES.MODAL;
afterEach(function () { return utils_1.removeSwal(); });
describe("show buttons", function () {
    test("shows only confirm button by default", function () {
        utils_1.swal();
        expect(utils_1.$$(BUTTON).length).toBe(1);
        expect(utils_1.$$(BUTTON).hasClass(CONFIRM_BUTTON)).toBeTruthy();
    });
    test("hides all buttons", function () {
        utils_1.swal({
            buttons: false
        });
        expect(utils_1.$$(BUTTON).length).toBe(0);
    });
    test("shows confirm and cancel buttons", function () {
        utils_1.swal({
            buttons: true
        });
        expect(utils_1.$$(BUTTON).length).toBe(2);
        expect(utils_1.$$(CONFIRM_BUTTON).length).toBe(1);
        expect(utils_1.$$(CANCEL_BUTTON).length).toBe(1);
    });
    test("sets button text", function () {
        utils_1.swal({
            button: "Test"
        });
        expect(utils_1.$$(CONFIRM_BUTTON).text()).toBe("Test");
    });
    test("sets button texts with array", function () {
        utils_1.swal({
            buttons: ["Stop", "Do it"]
        });
        expect(utils_1.$$(CONFIRM_BUTTON).text()).toBe("Do it");
        expect(utils_1.$$(CANCEL_BUTTON).text()).toBe("Stop");
    });
    test("sets default button texts with array", function () {
        utils_1.swal({
            buttons: [true, true]
        });
        expect(utils_1.$$(CONFIRM_BUTTON).text()).toBe("OK");
        expect(utils_1.$$(CANCEL_BUTTON).text()).toBe("Cancel");
    });
    test("uses button object", function () {
        utils_1.swal({
            buttons: {
                cancel: "Run away!",
                confirm: true
            }
        });
        expect(utils_1.$$(CANCEL_BUTTON).text()).toBe("Run away!");
        expect(utils_1.$$(CONFIRM_BUTTON).text()).toBe("OK");
    });
    test("sets more than 2 buttons", function () {
        utils_1.swal({
            buttons: {
                cancel: "Run away!",
                catch: {
                    text: "Throw Pokéball!"
                },
                defeat: true
            }
        });
        expect(utils_1.$$(BUTTON).length).toBe(3);
        expect(utils_1.$$(CANCEL_BUTTON).text()).toBe("Run away!");
        expect(utils_1.$$(CONFIRM_BUTTON).length).toBe(0);
        expect(utils_1.$$(BUTTON + "--catch").length).toBe(1);
        expect(utils_1.$$(BUTTON + "--catch").text()).toBe("Throw Pokéball!");
        expect(utils_1.$$(BUTTON + "--defeat").length).toBe(1);
        expect(utils_1.$$(BUTTON + "--defeat").text()).toBe("Defeat");
    });
});
describe("buttons resolve values", function () {
    test("confirm button resolves to true", function () __awaiter(this, void 0, void 0, function* () {
        expect.assertions(1);
        setTimeout(function () {
            utils_1.$$(CONFIRM_BUTTON).click();
        }, 500);
        var value = yield utils_1.swal();
        expect(value).toBeTruthy();
    }));
    test("cancel button resolves to null", function () __awaiter(this, void 0, void 0, function* () {
        expect.assertions(1);
        setTimeout(function () {
            utils_1.$$(CANCEL_BUTTON).click();
        }, 500);
        var value = yield utils_1.swal({
            buttons: true
        });
        expect(value).toBeNull();
    }));
    test("can specify resolve value", function () __awaiter(this, void 0, void 0, function* () {
        expect.assertions(1);
        setTimeout(function () {
            utils_1.$$(CONFIRM_BUTTON).click();
        }, 500);
        var value = yield utils_1.swal({
            button: {
                value: "test"
            }
        });
        expect(value).toBe("test");
    }));
    test("extra button resolves to string by default", function () __awaiter(this, void 0, void 0, function* () {
        expect.assertions(1);
        setTimeout(function () {
            utils_1.$("." + BUTTON + "--test").click();
        }, 500);
        var value = yield utils_1.swal({
            buttons: {
                test: true
            }
        });
        expect(value).toBe("test");
    }));
});
describe("loading", function () {
    test("shows loading state", function () __awaiter(this, void 0, void 0, function* () {
        utils_1.swal({
            button: {
                text: "HEPP",
                closeModal: false
            }
        });
        var $button = utils_1.$("." + BUTTON + "--confirm");
        expect($button.hasClass('swal-button--loading')).toBeFalsy();
        $button.click();
        expect($button.hasClass('swal-button--loading')).toBeTruthy();
    }));
});
describe("set class name", function () {
    test("sets single class name as string", function () __awaiter(this, void 0, void 0, function* () {
        utils_1.swal({
            button: {
                text: "TEST",
                closeModal: true,
                className: 'single-class'
            }
        });
        var $button = utils_1.$("." + BUTTON + "--confirm");
        expect($button.hasClass('single-class')).toBeTruthy();
    }));
    test("sets multiple class names as string", function () __awaiter(this, void 0, void 0, function* () {
        utils_1.swal({
            button: {
                text: "TEST",
                closeModal: true,
                className: 'class1 class2'
            }
        });
        var $button = utils_1.$("." + BUTTON + "--confirm");
        expect($button.hasClass('class1')).toBeTruthy();
        expect($button.hasClass('class2')).toBeTruthy();
    }));
    test("sets multiple class names as array", function () __awaiter(this, void 0, void 0, function* () {
        utils_1.swal({
            button: {
                text: "TEST",
                closeModal: true,
                className: ["class1", "class2"]
            }
        });
        var $button = utils_1.$("." + BUTTON + "--confirm");
        expect($button.hasClass('class1')).toBeTruthy();
        expect($button.hasClass('class2')).toBeTruthy();
    }));
});
//# sourceMappingURL=buttons.test.js.map