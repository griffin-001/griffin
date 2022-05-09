import React, {FunctionComponent} from 'react';
import LandingTopPanel from "./LandingTopPanel";
import NavBar from "../../components/NavBar/NavBar";
import PageContainer from "../../components/PageContainer";

interface Props {

}

// todo NOTE: All local components / hooks / utils etc.
//  can just go within the landing folder in views
//  Only the global stuff needs to go in a separate folder

class Landing extends React.Component<Props> {
  render() {

    return (
      <PageContainer isCentered>
        <LandingTopPanel/>
      </PageContainer>
    );
  }
}

export default Landing;