import React, {FunctionComponent} from 'react';
import CenteredBox from "../../components/CenteredBox";
import SubHeading from "../../components/Text/SubHeading";
import PageContainer from "../../components/PageContainer";

interface Props {

}

const Statistics: FunctionComponent<Props> = (props) => {

  return (
    <PageContainer centered>
      <SubHeading>
        Statistics and graphs - Coming soon...
      </SubHeading>
    </PageContainer>
  );
};

export default Statistics;