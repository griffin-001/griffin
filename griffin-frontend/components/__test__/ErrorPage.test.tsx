import {render, unmountComponentAtNode} from "react-dom";
import { act } from "react-dom/test-utils";
import ErrorPage from "../ErrorPage";

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

describe("<ErrorPage />", () => {
    it("Should have Title", () => {
        act(() => {
            render(<ErrorPage/>, container);
        });
        expect(container.textContent).toContain("Error");
    })
})