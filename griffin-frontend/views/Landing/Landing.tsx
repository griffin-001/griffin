import React, { FunctionComponent } from 'react';
import LandingTopPanel from "./LandingTopPanel";

interface Props {

}

// todo NOTE: All local components / hooks / utils etc.
//  can just go within the landing folder in views
//  Only the global stuff needs to go in a separate folder

const Landing: FunctionComponent<Props> = (props) => {

  return (
    <React.Fragment>
      <LandingTopPanel/>
      <LandingTopPanel/>
    </React.Fragment>
  );
};

export default Landing;