import BarChart from "./BarChart";
import AreaChart from "./AreaChart";
import Wrapper from "../../assets/css/ChartsContainer";
import { MonthlyApplication } from "../../utils/StatsResponse";
import { useState } from "react";

type Props = {
    data: MonthlyApplication[];
};

const ChartsContainer = ({ data }: Props) => {
    const [barChart, setBarChart] = useState(true);

    return <Wrapper>
      <h4>Monthly Applications</h4>
      <button type="button" onClick={() => setBarChart(!barChart)}>
        {barChart ? 'Area Chart' : 'Bar Chart'}
      </button>
      {barChart ? <BarChart data={data} /> : <AreaChart data={data} />}
    </Wrapper>;
};

export default ChartsContainer;
