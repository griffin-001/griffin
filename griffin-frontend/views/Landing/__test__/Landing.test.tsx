import {render, unmountComponentAtNode} from "react-dom";
import { act } from "react-dom/test-utils";
import LeftLandingPanel from "../components/LeftLandingPanel";
import RightLandingPanel from "../components/RightLandingPanel";
import { useRouter } from "next/router";




let container: any = null;

beforeEach(() => {
    container = document.createElement("div");
    document.body.appendChild(container);
})

afterEach(() => {
    unmountComponentAtNode(container);
    container.remove();
    container = null;
})

describe("<LeftLandingPanel />", () => {
    it("should have griffin logo", () => {
        act(() => {
            render(<LeftLandingPanel />, container);
        });
        const image = container.querySelector("[data-testid=griffin-logo]")
        expect(image.src).toContain("/assets/griffin-logo.png");
    })
})

describe("<RightLandingPanel />", () => {
    it('should have a button', function () {
        act(() => {
            render(<RightLandingPanel/>, container);
        });
        const button = container.querySelector("[data-testid=landing-page-button]")
        expect(button.textContent).toContain("Get Started");
    });
})