#File generator to be used for the results portion of our OS project according to specs
#
# authors: Phong Huynh, Shannon Lewis, Zach Soohoo, Rachel Chu
# Group #: 32
# Winter 2014

use Math::Random::OO::Normal; #generates random numbers based on normal distribution in decimal
use POSIX; #generates rounded numbers (using ceil and floor)

$maxMem = 100; #set this depending on the amount of memory we want the CPU scheduler to start off with
#determines the range of values for MemNeeded (from 0 to maxMem)

#print("Hi!!! This is Perl \n");

#practice use of the generator
#$firstGenerator = Math::Random::OO::Normal -> new(5);
#$firstValue = $firstGenerator -> next();
#print("Value is $firstValue");

#open the file to write to. File is our file handle
#writes following the existing data (so make sure fed file is empty)

#want to be able to call it like perl RandomFileGenerator.pl filename muBurst muPrio muMem numOfEntries
#each time you run you have to fill in all of the stuff

if($#ARGV+1 != 5)
{
    die "Not valid program call! Need filename, average burst, average priority
    , average memory needed, and number of entries";
}

$fileName = @ARGV[0];
$muB = @ARGV[1];
$muP = @ARGV[2];
$muM = @ARGV[3];
$numOfE = @ARGV[4];

open(File, ">>$fileName") || die "Can't open the file";

sub buildFile
{    
    @params = @_;
    
   #let range be 0 - 2*$mu where $mu is the mean value we want
    #and since we want to divide it into 6 parts
   #range by default would be (2*mu)/3 but if part of the range
   #falls out of the range given below we reroll   
    
    if($#params+1 < 4)
    {
       print("Incorrect Number Of Arguements!");
       print("$#params");
    }
    else
    {
    
    #CPU burst times between 1 and 99 (normalized around mu)
    #Delay times between 0 and 69
    #Priorities between 0 and 9 (normalized around mu)
    
    $numOfLines = @params[3];
    
    $muBurstTime = @params[0];
    $burstSTD = 99/6;
    $muPriority = @params[1];
    $prioritySTD = 9/6;
    $muMemoryNeed = @params[2];
    $memorySTD = $maxMem/6;
    
    $burstTimeGenerator = Math::Random::OO::Normal->new($muBurstTime, $burstSTD);
    $priorityGenerator = Math::Random::OO::Normal->new($muPriority, $prioritySTD);
    $memoryNeedGenerator = Math::Random::OO::Normal->new($muMemoryNeed, $memorySTD);
    $burstTime = 0;
    $priority = 9;

    
    for($i = 0; $i < $numOfLines; $i++)
    {
        if($i >= 1)
        {
            print File ("\n");
        }
        
        $validBurst = 0;
        $validPriority = 0;
        
        $entryTime = int(rand(70)); #generates a value within 0-69 automatically
        
        do
        {
            $burstTime = ceil($burstTimeGenerator->next());
            
            if($burstTime > 0 && $burstTime <= 100)
            {
                $validBurst = 1;
            }
        
        } while($validBurst == 0);
        
        do
        {
            $priority = ceil($priorityGenerator->next());
            
            if($priority >= 0 && $priority <= 9)
            {
                $validPriority = 1;
            }
            
        }while($validPriority == 0);
        
        #memory cannot be less than 0 but cannot be over the max
        # just to avoid suspension from happening in our stats when running those that don't
        #involve the memory constraint, if I say muM is 0, all memory fields are 0
        if($muM == 0)
        {
            $memory = 0;
        }
        else
        {
            $validMemAmount = 0;
            do
            {
                $memory = ceil($memoryNeedGenerator->next());
                if($memory >= 0)
                {
                    $validMemAmount = 1;
                }
            
            }while($validMemAmount == 0);
        }
        
        print File ("$burstTime $entryTime $priority $memory");
        #print "$burstTime $delayTime $priority\n";
    }
    
    }
}

#buildFile(muBurst, muPriority, muMem, numOfEntries)
buildFile($muB, $muP, $muM, $numOfE);

close(File);