"use strict";
var utils_1 = require('./utils');
var CONTENT = utils_1.CLASS_NAMES.CONTENT, MODAL_TEXT = utils_1.CLASS_NAMES.MODAL_TEXT;
afterEach(function () { return utils_1.removeSwal(); });
describe("show content", function () {
    test("shows no content by default", function () {
        utils_1.swal("Hello");
        expect(utils_1.$$(CONTENT).length).toBe(0);
    });
    test("shows input when using content: 'input'", function () {
        utils_1.swal({
            content: "input"
        });
        var inputSelector = CONTENT + "__input";
        expect(utils_1.$$(CONTENT).length).toBe(1);
        expect(utils_1.$$(inputSelector).length).toBe(1);
        expect(utils_1.$$(inputSelector).get(0).getAttribute('type')).toBeNull();
    });
    test("can customize input with more advanced content options", function () {
        utils_1.swal({
            content: {
                element: "input",
                attributes: {
                    placeholder: "Type your password",
                    type: "password"
                }
            }
        });
        var inputSelector = CONTENT + "__input";
        expect(utils_1.$$(CONTENT).length).toBe(1);
        expect(utils_1.$$(inputSelector).length).toBe(1);
        expect(utils_1.$$(inputSelector).get(0).getAttribute('type')).toBe('password');
        expect(utils_1.$$(inputSelector).get(0).getAttribute('placeholder')).toBe('Type your password');
    });
    test("can set content to custom DOM node", function () {
        var btn = document.createElement('button');
        btn.classList.add('custom-element');
        utils_1.swal({
            content: btn
        });
        expect(utils_1.$$(CONTENT).length).toBe(1);
        expect(utils_1.$$('custom-element').length).toBe(1);
    });
});
describe("show modal text", function () {
    test("transforms newline to break", function () {
        utils_1.swal('Hello\nWorld\n');
        expect(utils_1.$("." + MODAL_TEXT + " br").length).toBe(2);
    });
    test("escapes HTML elements", function () {
        var text = '<script>bad stuff</script>';
        utils_1.swal(text);
        expect(utils_1.$("." + MODAL_TEXT + " script").length).toBe(0);
        expect(utils_1.$$(MODAL_TEXT).text()).toEqual(expect.stringMatching(text));
    });
});
//# sourceMappingURL=content.test.js.map