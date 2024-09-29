import React from 'react'

const TransactionCard = ({item}) => {
  return (
    <div className='w-full py-2 px-8 border-b-2 border-b-slate-300  h-[20vh]'>

        <h2 className='font-semibold' ><span className='text-blue-600 text-lg '>Transaction </span>{item?.uniqueId}</h2>
        <div className='px-4'>

        
        <h2 className='font-semibold' ><span className='text-slate-500 text-md '>Book </span>{item?.book.name} ({item?.book.isbn})</h2>
        <h2 className='font-semibold' ><span className='text-slate-500 text-md '>User Id </span>{item?.userId}</h2>
        <h2 className='font-semibold' ><span className='text-slate-500 text-md '>Borrowed at </span>{item?.borrowDate}</h2>
        <h2 className='font-semibold' ><span className='text-slate-500 text-md '>Due to </span>{item?.dueDate}</h2>
        {item?.lateDates>0?(
            <div>
        <h2 className='font-semibold' ><span className='text-red-500 text-md '>Late </span>{item?.lateDates}</h2>
        <h2 className='font-semibold' ><span className='text-red-500 text-md '>Fine </span>{item?.lateDates*5                                                                                                                                                                                                                                                                                                                                                                       }</h2>
            </div>
        ):("")}
        
        </div>




    </div>
  )
}

export default TransactionCard