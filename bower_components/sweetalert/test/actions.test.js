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
var OVERLAY = utils_1.CLASS_NAMES.OVERLAY, CONFIRM_BUTTON = utils_1.CLASS_NAMES.CONFIRM_BUTTON, TITLE = utils_1.CLASS_NAMES.TITLE;
afterEach(function () { return utils_1.removeSwal(); });
describe("promise value", function () {
    test("dismisses modal by clicking on overlay", function () __awaiter(this, void 0, void 0, function* () {
        expect.assertions(1);
        setTimeout(function () {
            utils_1.$$(OVERLAY).click();
        }, 500);
        var value = yield utils_1.swal();
        expect(value).toBeNull();
    }));
    test("changes value with setActionValue", function () __awaiter(this, void 0, void 0, function* () {
        setTimeout(function () {
            utils_1.swal.setActionValue("test");
            utils_1.$$(CONFIRM_BUTTON).click();
        }, 500);
        var value = yield utils_1.swal();
        expect(value).toEqual("test");
    }));
    test("changes cancel value with setActionValue", function () __awaiter(this, void 0, void 0, function* () {
        setTimeout(function () {
            utils_1.swal.setActionValue({
                cancel: "test"
            });
            utils_1.$$(OVERLAY).click();
        }, 500);
        var value = yield utils_1.swal();
        expect(value).toEqual("test");
    }));
    /*
     * @TODO!
     *
    test("cannot dismiss if 'clickOutside' is false", async () => {
  
    });
     */
});
//# sourceMappingURL=actions.test.js.map