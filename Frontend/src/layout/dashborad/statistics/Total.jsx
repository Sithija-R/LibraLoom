import { formatNumber } from "../../../utils/utils";

function Card({ className, cardTitle ,Icon, title1,total }) {


  return (
    <div className={`${className} flex flex-col col-span-full sm:col-span-6 xl:col-span-4 bg-white dark:bg-slate-800 shadow-lg rounded-lg border border-slate-200 dark:border-slate-700`}>
      <div className="p-5">
        <header className="flex justify-between items-start mb-2">
          <img src={Icon} width="20" height="20" alt="Icon 02" />
          <h2>{cardTitle}</h2>
        </header>
        <div className="flex items-start">
         <h2 className="text-lg font-semibold">{title1}</h2>
        </div>
        <h2 className=" text-slate-800 dark:text-slate-100 mb-2">{total}</h2>
      </div>
    </div>
  );
}

export default Card;
