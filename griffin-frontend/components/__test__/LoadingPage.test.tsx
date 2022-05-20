import {render, unmountComponentAtNode} from "react-dom";
import { act } from "react-dom/test-utils";
import LoadingPage from "../LoadingPage";

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

describe("<LoadingPage />", () => {
    it("Should render Loading", () => {
        act(() => {
            render(<LoadingPage/>, container);
        });
        expect(container.textContent).toContain("Loading");
    })
})