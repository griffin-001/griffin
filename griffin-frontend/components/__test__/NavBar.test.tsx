import {render, unmountComponentAtNode} from "react-dom";
import { act } from "react-dom/test-utils";
import NavBar from "../NavBar/NavBar";

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

describe("<NavBar />", () => {
    it("Should have title and navigation", () => {
        act(() => {
            render(<NavBar/>, container);
        });
        expect(container.textContent).toContain("Griffin");
        expect(container.textContent).toContain("Scanner");
        expect(container.textContent).toContain("Statistics");
        expect(container.textContent).toContain("Advanced");
        expect(container.textContent).toContain("Log out");
    })
})
