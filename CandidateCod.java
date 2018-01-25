#include<stdio.h>
#include<string.h>
#include<stdio.h>
#include<string.h>
#include<iostream>
#include<vector>
#include<map>
#include<algorithm>

std::vector<std::string> getVect(char* input1[])
{
	std::vector<std::string> input;
	for(int i = 1; input1[0][i] != ' '; i++)
	{
		bool flag = false;
		std::string ss("");
		for(; input1[0][i] != '\0'; i++)
		{
			if (input1[0][i] == ' ')
			{
				flag = true;
				break;
			}
			ss += input1[0][i];
		}
		input.push_back(ss);
		if (flag)
			break;
	}
	return input;

}
int numberTime(std::string s) {
	int pos = s.find_first_of('A'), val = 0;
	if (pos > 0)
		val = atoi(s.substr(0,pos).c_str());
	else
	{
		pos = s.find_first_of('P');
		val = atoi(s.substr(0, pos).c_str());
		if (val < 12)
			val += 12;
	}
	return val;
}
int jobMachine(char* input1[])
{
	std::vector<std::string> input = getVect(input1);
	//std::cout<<input.size(); // return i.size();
	//std::cout<<"\n\n\n";
	std::vector<std::pair<int,int> > jobs;
	for(int i = 0; i < input.size(); i++) {
		int pos = input[i].find_first_of('#');
		std::string start_time = input[i].substr(0, pos), end_time = input[i].substr(pos+1);
		int start = numberTime(start_time), end = numberTime(end_time);
		jobs.push_back(std::make_pair(start, end - start));
		//std::cout<<start<<"  "<<end<<"  "<<end - start<<"\n";
	}
	std::sort(jobs.begin(), jobs.end());
	/*
	std::cout<<std::endl;
	for(std::vector<std::pair<int,int> >::iterator it = jobs.begin(); it != jobs.end(); ++it) {
		std::cout<<(*it).first<<"  "<<(*it).second<<"\n";
	}
	std::cout<<std::endl;
	*/
	std::vector<std::pair<int,int> > fin;
	for (int start = 5; start < 23;)
	{
		std::vector<std::pair<int,int> > curr;
		for(int i = 0; i < jobs.size(); i++)
		{
			if (jobs[i].first == start)
			{
				curr.push_back(jobs[i]);
				jobs.erase(jobs.begin() + i);
			}
		}
		//std::cout<<"\n\n"<<start<<"  "<<curr.size()<<"\n\n";
		if (curr.size() < 1)
		{
			start++;
			continue;
		}
		std::pair<int,int> min = std::make_pair(start,9999);
		for(int i = 0; i < curr.size(); i++)
		{
			if (curr[i].second < min.second)
			{
				min = curr[i];
			}
		}
		int max_diff = 0;
		for(int i = 0; i < jobs.size(); i++)
		{
			if (jobs[i].first < start)
				continue;
			int diff = (min.first + min.second) - (jobs[i].first + jobs[i].second);
			if (diff > max_diff)
			{
				max_diff = diff;
				min = jobs[i];
				start = jobs[i].first;
			}
		}
		if ((min.first + min.second) > 23)
			break;
		fin.push_back(min);
		start += min.second;
	}
		
	/*
	std::cout<<std::endl<<"FINISHED\n\n";
	for(std::vector<std::pair<int,int> >::iterator it = fin.begin(); it != fin.end(); ++it) {
		std::cout<<(*it).first<<"  "<<(*it).second<<"\n";
	}
	std::cout<<std::endl;
	*/
	
	std::cout<<fin.size();
}
int main()
{
char* s[] = {"{6PM#10PM","11AM#2PM","12PM#1PM","1PM#3PM","6AM#8AM "};
jobMachine(s);
return 0;
}