import { useMyContext } from '../../context/ContextProvider';
import Icon from '../../assets/icons/WelcomeSVG';

function WelcomeBanner() {

  const { user = "Anonymous" } = useMyContext()


  return (
    <div className="relative bg-indigo-200 dark:bg-indigo-500 p-4 sm:p-6 rounded-sm overflow-hidden mb-8">
      {/* Background illustration */}
      <div className="absolute right-0 top-0 -mt-4 mr-16 pointer-events-none hidden lg:block" aria-hidden="true">
        <Icon />
      </div>

      <div className="relative">
        welcome to
        <h1 className="text-2xl md:text-3xl text-slate-800 dark:text-slate-100 font-bold mb-1">LibraLoom {user?.userName}. 👋</h1>
        <p className="capitalize dark:text-indigo-200">Forge Your Destiny: Where Every Book Is a Key!</p>
      </div>
    </div>
  );
}

export default WelcomeBanner;
