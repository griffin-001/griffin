import React, {FunctionComponent} from 'react';
import VulnerabilitiesReport
  from "../views/VulnerabiltiesReport/VulnerabilitiesReport";

interface Props {

}

const TestDeps = [
  {name : "Dependency 1",
    description : "This is a dependency",
    version : "1.4.6"},
  {name : "Dependency 2",
    description : "This is another dependency",
    version : "1.1.5"}
]

const TestVulns = [
  {name : "Vulnerability 1",
    description : "This is a vulnerability",
    dependency : TestDeps[0]},
  {name : "Vulnerability 2",
    description : "This is another vulnerability",
    dependency : TestDeps[0]
  },
  {name : "Vulnerability 3",
    description : "This is another vulnerability",
    dependency : TestDeps[0]
  },
  {name : "Vulnerability 4",
    description : "This is another vulnerability",
    dependency : TestDeps[0]
  },
  {name : "Vulnerability 5",
    description : "This is another vulnerability",
    dependency : TestDeps[0]
  },
  {name : "Vulnerability 6",
    description : "This is another vulnerability",
    dependency : TestDeps[0]
  },
  {name : "Vulnerability 7",
    description : "This is another vulnerability",
    dependency : TestDeps[0]
  },
  {name : "Vulnerability 8",
    description : "This is another vulnerability",
    dependency : TestDeps[0]
  },
  {name : "Vulnerability 9",
    description : "This is another vulnerability",
    dependency : TestDeps[0]
  },
  {name : "Vulnerability 10",
    description : "This is another vulnerability",
    dependency : TestDeps[0]
  },
  {name : "Vulnerability 11",
    description : "This is another vulnerability",
    dependency : TestDeps[0]
  },
  {name : "Vulnerability 12",
    description : "This is another vulnerability",
    dependency : TestDeps[0]
  },
  {name : "Vulnerability 13",
    description : "This is another vulnerability",
    dependency : TestDeps[0]
  },
  {name : "Vulnerability 14",
    description : "This is another vulnerability",
    dependency : TestDeps[0]
  }]

const vulnerabilitiesReport: FunctionComponent<Props> = (props) => {
  return <VulnerabilitiesReport vulnerabilities={TestVulns}/>
};

export default vulnerabilitiesReport;