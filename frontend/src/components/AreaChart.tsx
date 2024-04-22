import {
    ResponsiveContainer,
    AreaChart,
    Area,
    XAxis,
    YAxis,
    CartesianGrid,
    Tooltip,
} from "recharts";
import { MonthlyApplication } from "../utils/StatsResponse";

type Props = {
    data: MonthlyApplication[];
};

const AreaChartComponent = ({ data }: Props) => {
    return (
        <ResponsiveContainer width="100%" height={300}>
            <AreaChart data={data} margin={{ top: 50 }}>
                <CartesianGrid strokeDasharray="3 3" />
                <XAxis dataKey="date" />
                <YAxis allowDecimals={false} />
                <Tooltip />
                <Area
                    type="monotone"
                    dataKey="count"
                    stroke="#2cb1bc"
                    fill="#bef8fd"
                />
            </AreaChart>
        </ResponsiveContainer>
    );
};
300;
export default AreaChartComponent;
