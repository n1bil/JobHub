import { FaSuitcaseRolling, FaCalendarCheck, FaBug } from "react-icons/fa";
import Wrapper from "../../assets/css/StatsContainer";
import StatItem from "../StatItem";
import { DefaultStats } from "../../utils/StatsResponse";

type Props = {
    defaultStats: DefaultStats;
};

const StatsContainer = ({ defaultStats }: Props) => {
    const stats = [
        {
            title: "pending applications",
            count: defaultStats?.pending || 0,
            icon: <FaSuitcaseRolling />,
            color: "#f59e0b",
            bcg: "#fef3c7",
        },
        {
          title: "interview scheduled",
          count: defaultStats?.interview || 0,
          icon: <FaCalendarCheck />,
          color: "#647acb",
          bcg: "#e0e8f9",
      },
      {
        title: "jobs declined",
        count: defaultStats?.declined || 0,
        icon: <FaBug />,
        color: "#d66a6a",
        bcg: "#ffeeee",
    },
    ];

    return (
        <Wrapper>
            {stats.map((item) => {
                return <StatItem key={item.title} {...item} />;
            })}
        </Wrapper>
    );
};

export default StatsContainer;
