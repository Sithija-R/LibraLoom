import { useMyContext } from '../context/ContextProvider';
import useBorrowingNormalizer from '../data/dataNormalizer/borrowing';

import Icon1 from '../assets/icons/icon-01.svg';
import Icon2 from '../assets/icons/icon-02.svg';
import Icon3 from '../assets/icons/icon-03.svg';

import WelcomeBanner from '../layout/dashborad/WelcomeBanner';
import Total from '../layout/dashborad/statistics/Total';
import { BookCard } from '../layout/dashborad/statistics/BookCard';
import { useDispatch, useSelector } from 'react-redux';
import { useEffect } from 'react';
import { getAllBooks } from '../../Storage/Book/Action';


function Dashboard() {
  const { data } = useMyContext();
  const dispatch = useDispatch();

  const {book}=useSelector(store=>store);

  useEffect(()=>{
    dispatch(getAllBooks());
  },[])


  const {
    totalUsers,
    totalBooks,
    totalAuthors,
  } = useBorrowingNormalizer(data)


  return (
    <main>
      <div className="px-4 h-[92vh] mt-10 sm:px-6 lg:px-8 py-8 w-full max-w-9xl mx-auto">
        <WelcomeBanner />

        <div className="grid grid-cols-12 gap-6">

          {/* Display total of x */}
          <Total Icon={Icon1} title1="Sherlock Holmes" total={totalUsers} cardTitle={"Borrowed Books"}/>
          <Total Icon={Icon2} title1="Books to Explore" total={totalBooks} />
          <Total Icon={Icon3} title1="Authors Featured" total={totalAuthors} />
        


        </div>
          <h2 className='mt-2 font-semibold text-lg'>Available Books</h2>
        <div className='h-[45vh] overflow-y-scroll mt-4 space-y-2'>
        {book.books?.map((item) => (
  <BookCard item={item} />
))}

        </div>
      </div>
    </main >
  );
}

export default Dashboard;