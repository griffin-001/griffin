import React, {FunctionComponent} from 'react';
import CenteredBox from "../../components/CenteredBox";
import SubHeading from "../../components/Text/SubHeading";
import PageContainer from "../../components/PageContainer";

interface Props {

}

const Advanced: FunctionComponent<Props> = (props) => {

  return (
    <PageContainer centered>
      <SubHeading>
        Advanced configurations and CI Pipeline integration - Coming soon...
      </SubHeading>
    </PageContainer>
  );
};

export default Advanced;