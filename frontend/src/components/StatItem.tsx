import Wrapper from "../assets/css/StatItem";

type Props = {
    count: number;
    title: string;
    icon: JSX.Element
    color: string;
    bcg: string;
};

const StatItem = ({ count, title, icon, color, bcg }: Props) => {
    return (
        <Wrapper color={color} bcg={bcg}>
            <header>
                <span className="count">{count}</span>
                <span className="icon">{icon}</span>
            </header>
            <h5 className="title">{title}</h5>
        </Wrapper>
    );
};

export default StatItem;
