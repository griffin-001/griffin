import React, {FunctionComponent, useState} from 'react';

interface Props {
  firstTab: JSX.Element;
  secondTab: JSX.Element;
}

enum CurrentTab {
  First,
  Second,
  Thirds,
  // etc...
}


const Tabs: FunctionComponent<Props> = (props) => {

  const [tab, setTab] = useState(CurrentTab.First);

  // todo implement mui tabs here
  return (
    <React.Fragment>

    </React.Fragment>
  );
};

export default Tabs;