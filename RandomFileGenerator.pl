use Math::Random::OO::Normal;
print("Hi!!! This is Perl \n");

#This generator returns decimals...

#practice use of the generator
#$firstGenerator = Math::Random::OO::Normal -> new(5);
#$firstValue = $firstGenerator -> next();
#print("Value is $firstValue");

#open the file to write to. File is our file handle
#hopefully its empty... otherwise I'm overwritting anything in it...

open(File, "fileNameHere") || die "Can't open the file");

sub buildFile
{    
    @params = @_;
    
   #let range be 0 - 2*$mu where $mu is the mean value we want
    #and since we want to divide it into 6 parts...       
    
    if($#params < 4)
    {
       print("Incorrect Number Of Arguements!");
    }
    else
    {
    
    $numOfLines = @params[3];
    
    $muEntranceTime = @params[0];
    $entranceSTD = $muEntranceTime/3;
    $muBurstTime = @params[1];
    $burstSTD = $muBurstTime/3;
    $muPriority = @params[2];
    $prioritySTD = $muPriority/3;
    
    $entryGenerator = Math::Random::OO::Normal->new($muEntranceTime, $entranceSTD);
    $burstTimeGenerator = Math::Random::OO:Normal->new($muBurstTime, $burstSTD);
    $priorityGenerator = Math::Random::OO:Normal->new($muPriority, $prioritySTD);
    
    for($i = 0; i < $numOfLines; i++)
    {
        $entryTime = $entryGenerator->next();
        $burstTime = $burstTimeGenerator ->next();
        $priority = $priorityGenerator->next();
        
        print File "$entryTime $burstTime $priority \n";
    }
    
    }
}

buildFile($first, $second, $third, $four);

close(File);

# practice test method
#sub zeroToNum
#{
 #   print("Practice Method \n");
  #  $num = @_[0];
   # print("$num \n");
    #$sum = 0;
    #
    #for($i = 0; $i <= $num; $i++)
    #{
    #    #print("in loop \n");
    #    $sum = $sum + $i;
    #}
    
    #print("$sum\n");
#}*/

#zeroToNum(3);
#print("$threeSum \n");
#Should print 6